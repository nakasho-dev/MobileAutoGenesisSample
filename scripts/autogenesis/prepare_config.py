#!/usr/bin/env python3
import argparse
import json
import os
import pathlib
import subprocess
import sys
from typing import Optional


def load_config(path: pathlib.Path) -> dict:
    with path.open(encoding="utf-8") as stream:
        return json.load(stream)


def booted_simulator() -> Optional[dict]:
    result = subprocess.run(
        ["xcrun", "simctl", "list", "devices", "booted", "--json"],
        check=True,
        capture_output=True,
        text=True,
    )
    devices = json.loads(result.stdout).get("devices", {})
    for runtime, candidates in devices.items():
        for candidate in candidates:
            if candidate.get("state") == "Booted" and candidate.get("isAvailable", True):
                return {"runtime": runtime, **candidate}
    return None


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("platform", choices=("android", "ios"))
    parser.add_argument("--workspace", required=True, type=pathlib.Path)
    parser.add_argument("--output", required=True, type=pathlib.Path)
    args = parser.parse_args()

    workspace = args.workspace.resolve()
    source = workspace / "config" / f"appium_conf.{args.platform}.json"
    config = load_config(source)
    platform_config = config["APPIUM_DRIVER_CONFIGS"][args.platform]
    app_path = platform_config["appium:app"].replace("${WORKSPACE_ROOT}", str(workspace))
    platform_config["appium:app"] = app_path

    if not pathlib.Path(app_path).exists():
        print(f"App artifact not found: {app_path}", file=sys.stderr)
        return 1

    if args.platform == "android":
        platform_config["deviceName"] = os.environ.get(
            "ANDROID_DEVICE_ID", platform_config["deviceName"]
        )
    else:
        simulator = booted_simulator()
        if simulator is None:
            print("No booted iOS Simulator found.", file=sys.stderr)
            return 1
        platform_config["deviceName"] = simulator["name"]
        platform_config["udid"] = simulator["udid"]

    args.output.parent.mkdir(parents=True, exist_ok=True)
    with args.output.open("w", encoding="utf-8") as stream:
        json.dump(config, stream, ensure_ascii=True, indent=2)
        stream.write("\n")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())