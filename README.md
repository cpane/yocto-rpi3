# Yocto RPi3 Build Environment

This repository defines a Yocto Project build environment for Raspberry Pi 3, using a custom distribution (`cpane`) and layered configuration for reproducible, automated image generation.

## 🧱 Layers Used

This build environment includes the following layers:

- `poky` (Scarthgap)
- `meta-raspberrypi`
- `meta-openembedded` (meta-oe, meta-python, etc.)
- `meta-cpane` – Custom layer with:
  - Custom distribution: `cpane`
  - HWDB workaround
  - Image and systemd tweaks

## 🐧 Custom Distro: `cpane`

The `cpane` distro config:
- Uses `systemd` as the init manager
- Enables networking features (Ethernet, WiFi)
- Removes problematic `udev-hwdb` postinstall
- Adds useful debugging and dev tools
- Enforces policy and packaging consistent with project requirements

Defined in:  
`meta-cpane/conf/distro/cpane.conf`

## 🛠️ Setup Instructions

```bash
git clone https://github.com/YOUR_USERNAME/yocto-rpi3.git
cd yocto-rpi3

# Initialize and update submodules (if using submodules)
git submodule update --init --recursive

# Source the Yocto environment (from poky)
source poky/oe-init-build-env

# Copy template configs
cp ../local.conf.template conf/local.conf
cp ../bblayers.conf.template conf/bblayers.conf

# Set distro in local.conf (optional if using cpane.conf by default)
echo 'DISTRO = "cpane"' >> conf/local.conf

# Build the image
bitbake core-image-minimal

