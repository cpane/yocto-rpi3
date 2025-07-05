# Yocto RPi3 Build Environment

This repository defines a Yocto Project build environment for Raspberry Pi 3, using a custom distribution (`cpane`) and layered configuration for reproducible, automated image generation.

---

## 🧱 Layers Used

This build setup includes the following layers (checked out as submodules):

- `poky` (Scarthgap release)
- `meta-raspberrypi`
- `meta-openembedded` (includes `meta-oe`, `meta-networking`, `meta-python`, `meta-multimedia`)
- `meta-cpane` – Custom layer with:
  - Custom distribution: `cpane`
  - Systemd image tweaks
  - HWDB postinstall workaround
  - Useful packages for development and networking

---

## 🐧 Custom Distro: `cpane`

The `cpane` distro:
- Uses `systemd` as the init system
- Enables Ethernet and WiFi support
- Removes the problematic `udev-hwdb` postinstall
- Adds useful packages (`htop`, `curl`, `openssh`, etc.)
- Disables `sysvinit` for a cleaner systemd environment

Defined in:  
`meta-cpane/conf/distro/cpane.conf`

---

## 🛠️ Setup Instructions

Clone the repo and initialize submodules:

```bash
git clone https://github.com/YOUR_USERNAME/yocto-rpi3.git
cd yocto-rpi3
./scripts/setup-build.sh

