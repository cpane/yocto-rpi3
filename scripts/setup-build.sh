#!/bin/bash
set -e

# Pull submodules
git submodule update --init --recursive

# Set up build dir
source poky/oe-init-build-env build

# Copy templates
cp -n ../conf/local.conf.template conf/local.conf
cp -n ../conf/bblayers.conf.template conf/bblayers.conf

echo "Build environment is ready."

