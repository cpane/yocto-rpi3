#!/bin/bash
set -e

# Absolute path to this script
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
REPO_DIR="$(dirname "$SCRIPT_DIR")"

# Ensure we're in the top-level directory of the repo
cd "$REPO_DIR"

echo "📦 Initializing submodules..."
git submodule update --init --recursive

echo "🛠 Setting up build directory..."

# Set TEMPLATECONF to a valid template directory
export TEMPLATECONF="${REPO_DIR}/meta-cpane/conf/templates/cpane"

# Source Yocto's environment script
source poky/oe-init-build-env build

echo "✅ Build environment is ready in ./build/"
echo "➡️  Run 'bitbake core-image-minimal' to build your image"

