#!/usr/bin/env python3
import argparse
import asyncio
import os
import pathlib
import sys

from mcp.client.session import ClientSession
from mcp.client.stdio import StdioServerParameters, stdio_client


REQUIRED_TOOLS = {
    "before_gen_code",
    "preview_code_changes",
    "confirm_code_changes",
}


async def verify(platform: str) -> None:
    workspace = pathlib.Path(__file__).resolve().parents[2]
    sys.path.insert(0, str(workspace / "behave-demo"))
    from features.environment import load_mcp_config

    config = load_mcp_config(platform)
    parameters = StdioServerParameters(
        command=config["command"],
        args=config.get("args", []),
        env={**os.environ, **config.get("env", {})},
    )
    async with stdio_client(parameters) as streams:
        async with ClientSession(*streams) as session:
            await session.initialize()
            tools = {tool.name for tool in (await session.list_tools()).tools}

    missing = REQUIRED_TOOLS - tools
    if missing:
        raise RuntimeError(f"Missing MCP tools: {', '.join(sorted(missing))}")
    print(f"PASS: {platform} MCP exposes {len(tools)} tools")


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("platform", choices=("android", "ios"))
    args = parser.parse_args()
    asyncio.run(verify(args.platform))
    return 0


if __name__ == "__main__":
    raise SystemExit(main())