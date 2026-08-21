#!/bin/sh
set -eu

revision="${1:-158020978f651834912ea867b356845549f7a032}"
repository_url="https://github.com/microsoft/AutoGenesis"
workspace_root=$(CDPATH= cd -- "$(dirname "$0")/../.." && pwd)
vendor_root="$workspace_root/vendor"
target_dir="$vendor_root/AutoGenesis"
archive_dir="$vendor_root/AutoGenesis-$revision"

if [ -e "$target_dir" ]; then
    printf '%s\n' "Refusing to replace existing $target_dir"
    printf '%s\n' "Remove it after reviewing local changes, then rerun this script."
    exit 1
fi

mkdir -p "$vendor_root"
curl -L --fail --retry 3 "$repository_url/archive/$revision.tar.gz" | tar -xz -C "$vendor_root"
mv "$archive_dir" "$target_dir"

test -f "$target_dir/LICENSE"
test -f "$target_dir/appium-mcp-server/simple_server.py"
test -f "$target_dir/appium-mcp-server/pyproject.toml"

printf '%s\n' "Vendored AutoGenesis revision $revision"
printf '%s\n' "Update THIRD_PARTY_NOTICES.md and run mobile validation before committing."