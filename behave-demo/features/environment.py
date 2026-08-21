import asyncio
import json
import os
import pathlib
import threading
from concurrent.futures import TimeoutError as FutureTimeoutError

from mcp.client.session import ClientSession
from mcp.client.stdio import StdioServerParameters, stdio_client


MCP_SERVERS = {
    "android": "auto-genesis-mcp-android",
    "ios": "auto-genesis-mcp-ios",
}


def workspace_root() -> pathlib.Path:
    return pathlib.Path(__file__).resolve().parents[2]


def load_mcp_config(platform: str) -> dict:
    server_name = MCP_SERVERS.get(platform)
    if server_name is None:
        raise ValueError("AUTOGENESIS_PLATFORM must be 'android' or 'ios'.")

    config_path = workspace_root() / ".vscode" / "mcp.json"
    with config_path.open(encoding="utf-8") as stream:
        servers = json.load(stream).get("servers", {})

    if server_name not in servers:
        raise ValueError(f"MCP server '{server_name}' is not configured in {config_path}.")

    config = servers[server_name]
    if "command" not in config:
        raise ValueError(f"MCP server '{server_name}' must use stdio transport.")

    root = str(workspace_root())

    def expand(value):
        if isinstance(value, str):
            return value.replace("${workspaceFolder}", root)
        if isinstance(value, list):
            return [expand(item) for item in value]
        if isinstance(value, dict):
            return {key: expand(item) for key, item in value.items()}
        return value

    return expand(config)


def before_all(context):
    platform = os.environ.get("AUTOGENESIS_PLATFORM", "").lower()
    context.default_wait = 10
    context._mcp_ready = threading.Event()
    context._mcp_error = None
    context._mcp_loop = None
    context._mcp_stop = None

    def run_mcp():
        loop = asyncio.new_event_loop()
        asyncio.set_event_loop(loop)
        context._mcp_loop = loop

        async def connect():
            try:
                config = load_mcp_config(platform)
                parameters = StdioServerParameters(
                    command=config["command"],
                    args=config.get("args", []),
                    env={**os.environ, **config.get("env", {})},
                )
                async with stdio_client(parameters) as streams:
                    async with ClientSession(*streams) as session:
                        await session.initialize()
                        context.session = session
                        context._mcp_stop = asyncio.Event()
                        context._mcp_ready.set()
                        await context._mcp_stop.wait()
            except BaseException as error:
                context._mcp_error = error
                context._mcp_ready.set()

        try:
            loop.run_until_complete(connect())
        finally:
            loop.close()

    context._mcp_thread = threading.Thread(target=run_mcp, daemon=True)
    context._mcp_thread.start()

    if not context._mcp_ready.wait(timeout=60):
        raise TimeoutError("Timed out while starting the AutoGenesis MCP server.")
    if context._mcp_error is not None:
        raise RuntimeError("Failed to start the AutoGenesis MCP server.") from context._mcp_error


def after_all(context):
    loop = getattr(context, "_mcp_loop", None)
    stop = getattr(context, "_mcp_stop", None)
    if loop is not None and stop is not None and loop.is_running():
        loop.call_soon_threadsafe(stop.set)
    thread = getattr(context, "_mcp_thread", None)
    if thread is not None:
        thread.join(timeout=10)


def call_tool_sync(context, coroutine, timeout=400):
    loop = getattr(context, "_mcp_loop", None)
    if loop is None or not loop.is_running():
        coroutine.close()
        raise RuntimeError("AutoGenesis MCP event loop is not running.")
    future = asyncio.run_coroutine_threadsafe(coroutine, loop)
    try:
        return future.result(timeout=timeout)
    except FutureTimeoutError as error:
        future.cancel()
        raise TimeoutError("MCP tool invocation timed out.") from error


def get_tool_json(result):
    if isinstance(result, dict):
        return result
    if isinstance(result, str):
        try:
            return json.loads(result)
        except json.JSONDecodeError:
            return result

    for item in getattr(result, "content", []):
        text = getattr(item, "text", None)
        if text:
            try:
                return json.loads(text)
            except json.JSONDecodeError:
                return text
    return None
