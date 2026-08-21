#!/bin/sh
set -eu

workspace_root=$(CDPATH= cd -- "$(dirname "$0")/../.." && pwd)
exec python3 "$workspace_root/scripts/autogenesis/preflight.py" "$@"