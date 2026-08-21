#!/bin/sh
set -eu

platform="${1:-}"
case "$platform" in
    android|ios) ;;
    *) printf '%s\n' "Usage: $0 android|ios" >&2; exit 2 ;;
esac

workspace_root=$(CDPATH= cd -- "$(dirname "$0")/../.." && pwd)
server_root="$workspace_root/vendor/AutoGenesis/appium-mcp-server"
generated_config="$workspace_root/config/.generated/appium_conf.$platform.json"

python3 "$workspace_root/scripts/autogenesis/prepare_config.py" \
    "$platform" \
    --workspace "$workspace_root" \
    --output "$generated_config"

exec uv run --project "$server_root" python "$server_root/simple_server.py" \
    --platform "$platform" \
    --transport stdio \
    --config "$generated_config"