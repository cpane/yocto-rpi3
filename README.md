# Yocto RPi3 Build Environment

This repository defines a Yocto Project build environment for Raspberry Pi 3 using a custom distribution (`cpane`) and a structured layout for reproducible, automated builds.

---

## 🧱 Layers Used

This build setup includes the following layers as Git submodules:

- `poky` (Scarthgap release)
- `meta-raspberrypi`
- `meta-openembedded` (meta-oe, meta-networking, meta-python, meta-multimedia)
- `meta-cpane` – Custom layer with:
  - Custom distribution: `cpane`
  - HWDB workaround for systemd
  - Useful development and networking tools

---

## 🐧 Custom Distro: `cpane`

The custom distro is defined in `meta-cpane/conf/distro/cpane.conf`. Features:

- `systemd` as the init system
- Ethernet and WiFi support
- Common developer tools installed (e.g., `htop`, `wget`, `nano`)
- `udev-hwdb` blacklisted to avoid postinstall issues
- Image type set to `rpi-sdimg`
- Serial console enabled via `ttyAMA0`
- sysvinit explicitly removed for a clean systemd-only setup

---

## 🛠️ Setup Instructions

To initialize and build the environment from scratch:

```bash
# 1. Clone the repository and submodules
git clone https://github.com/YOUR_USERNAME/yocto-rpi3.git
cd yocto-rpi3

# 2. Source the setup script (this is important — DO NOT just run it)
source scripts/setup-build.sh

# 3. Build the image
bitbake core-image-minimal
```

This will produce an SD card image here:

```
build/tmp/deploy/images/raspberrypi3/core-image-minimal-raspberrypi3.rpi-sdimg
```

You can flash it to an SD card with:

```bash
sudo dd if=build/tmp/deploy/images/raspberrypi3/core-image-minimal-raspberrypi3.rpi-sdimg of=/dev/sdX bs=4M status=progress && sync
```

Replace `/dev/sdX` with the correct device node for your SD card.

---

## 📜 `scripts/setup-build.sh` Contents

You must **source** this script (e.g., `source scripts/setup-build.sh`) so that your environment is properly configured.

```bash
#!/bin/bash
set -e

# Get absolute path to repo root
BASE_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

# Point TEMPLATECONF to our custom template
export TEMPLATECONF="${BASE_DIR}/meta-cpane/conf/templates/cpane"

# Pull submodules
echo "📦 Initializing submodules..."
git submodule update --init --recursive

# Set up the build environment
echo "🛠 Setting up build directory..."
source "${BASE_DIR}/poky/oe-init-build-env" "${BASE_DIR}/build"

echo "✅ Build environment is ready in ./build/"
echo "➡️  Run 'bitbake core-image-minimal' to build your image"
```

---

## 📁 Directory Layout

```
yocto-rpi3/
├── build/                        # Created after setup
├── meta-cpane/                  # Your custom layer
│   ├── conf/
│   │   ├── distro/
│   │   │   ├── cpane.conf
│   │   │   └── include/nohwdb.inc
│   │   ├── layer.conf
│   │   └── templates/cpane/
│   │       ├── bblayers.conf.template
│   │       └── local.conf.template
│   ├── recipes-core/
│   │   ├── images/core-image-minimal.bbappend
│   │   └── systemd/systemd_%.bbappend
│   └── recipes-example/
│       └── example/example_0.1.bb
├── meta-openembedded/           # Submodule
├── meta-raspberrypi/            # Submodule
├── poky/                        # Submodule
└── scripts/
    └── setup-build.sh
```

---

## 🧹 Clean Build (Optional)

```bash
rm -rf build tmp sstate-cache
source scripts/setup-build.sh
bitbake core-image-minimal
```

---

## ✅ Result

After a successful build, you'll have an image you can flash to an SD card and boot on a Raspberry Pi 3. It will boot with:

- Systemd as PID 1
- Serial console on UART0
- SSH server enabled
- Networking tools pre-installed

---

## 🧑‍💻 Author

Chris Pane  
https://github.com/cpane

---

## 📜 License

MIT License – See `meta-cpane/COPYING.MIT`

