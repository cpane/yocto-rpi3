#!/bin/bash
set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
BASE_DIR="$(dirname "$SCRIPT_DIR")"

echo "📦 Initializing submodules..."
git submodule update --init --recursive

echo "🛠  Setting up build directory..."
export TEMPLATECONF="${BASE_DIR}/meta-cpane/conf/templates/cpane"
source "${BASE_DIR}/poky/oe-init-build-env" "${BASE_DIR}/build"

echo "✅ Build environment is ready."

