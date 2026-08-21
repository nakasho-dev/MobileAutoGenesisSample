#!/usr/bin/env python3
import argparse
import json
import os
import pathlib
import shutil
import socket
import subprocess
import sys
from typing import Optional


def command_path(name: str) -> Optional[str]:
    return shutil.which(name)


def run(command: list[str]) -> subprocess.CompletedProcess:
    return subprocess.run(command, capture_output=True, text=True, check=False)


def appium_drivers() -> str:
    result = run(["appium", "driver", "list", "--installed"])
    if result.returncode != 0:
        raise RuntimeError(result.stderr.strip() or "Unable to list Appium drivers.")
    return f"{result.stdout}\n{result.stderr}".lower()


def appium_is_listening() -> bool:
    try:
        with socket.create_connection(("127.0.0.1", 4723), timeout=1):
            return True
    except OSError:
        return False


def load_app_path(workspace: pathlib.Path, platform: str) -> pathlib.Path:
    config_path = workspace / "config" / f"appium_conf.{platform}.json"
    with config_path.open(encoding="utf-8") as stream:
        config = json.load(stream)
    raw_path = config["APPIUM_DRIVER_CONFIGS"][platform]["appium:app"]
    return pathlib.Path(raw_path.replace("${WORKSPACE_ROOT}", str(workspace)))


def booted_simulator() -> bool:
    result = run(["xcrun", "simctl", "list", "devices", "booted", "--json"])
    if result.returncode != 0:
        return False
    devices = json.loads(result.stdout).get("devices", {})
    return any(
        device.get("state") == "Booted" and device.get("isAvailable", True)
        for candidates in devices.values()
        for device in candidates
    )


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("platform", choices=("android", "ios"))
    args = parser.parse_args()
    workspace = pathlib.Path(__file__).resolve().parents[2]
    failures = []

    for command in ("uv", "appium"):
        if command_path(command) is None:
            failures.append(f"Missing command: {command}")

    server = workspace / "vendor" / "AutoGenesis" / "appium-mcp-server" / "simple_server.py"
    if not server.exists():
        failures.append(f"Vendored AutoGenesis server not found: {server}")

    app_path = load_app_path(workspace, args.platform)
    if not app_path.exists():
        failures.append(f"App artifact not found: {app_path}")

    if command_path("appium") is not None:
        try:
            drivers = appium_drivers()
            required_driver = "uiautomator2" if args.platform == "android" else "xcuitest"
            if required_driver not in drivers:
                failures.append(f"Appium driver is not installed: {required_driver}")
        except RuntimeError as error:
            failures.append(str(error))

    if not appium_is_listening():
        failures.append("Appium is not listening at http://127.0.0.1:4723")

    if args.platform == "android":
        if not os.environ.get("ANDROID_HOME") and not os.environ.get("ANDROID_SDK_ROOT"):
            failures.append("ANDROID_HOME or ANDROID_SDK_ROOT is not set")
        if command_path("adb") is None:
            failures.append("Missing command: adb")
        else:
            devices = run(["adb", "devices"]).stdout.splitlines()[1:]
            if not any(line.rstrip().endswith("\tdevice") for line in devices):
                failures.append("No ready Android device or emulator found")
    else:
        for command in ("xcodebuild", "xcrun"):
            if command_path(command) is None:
                failures.append(f"Missing command: {command}")
        if command_path("xcrun") is not None and not booted_simulator():
            failures.append("No booted iOS Simulator found")

    if failures:
        for failure in failures:
            print(f"FAIL: {failure}", file=sys.stderr)
        return 1

    print(f"PASS: AutoGenesis {args.platform} preflight")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())