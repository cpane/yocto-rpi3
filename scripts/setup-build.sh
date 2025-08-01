#!/bin/bash
set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
BASE_DIR="$(dirname "$SCRIPT_DIR")"

echo "📦 Initializing submodules..."
git submodule update --init --recursive

echo "🛠  Setting up build directory with cpane distribution..."
# Set TEMPLATECONF to use our cpane distribution templates
export TEMPLATECONF="${BASE_DIR}/meta-cpane/conf/templates/cpane"
cd "${BASE_DIR}"
source poky/oe-init-build-env build

echo "✅ Build environment ready for Raspberry Pi 3 with cpane distribution!"
echo "🚀 You can now run: bitbake core-image-minimal"
