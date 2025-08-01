# Yocto Raspberry Pi 3 with cpane Distribution

This repository provides a Yocto build setup for Raspberry Pi 3 using a custom "cpane" distribution with systemd, networking, and useful utilities pre-configured.

## Quick Start

### Option 1: Yocto-Native Approach (Recommended)
```bash
git clone --recursive <your-repo-url>
cd yocto-rpi3
source setup-env
bitbake core-image-minimal
```

### Option 2: Automated Setup Script
```bash
git clone --recursive <your-repo-url>
cd yocto-rpi3
./scripts/setup-build.sh
```

### Option 3: Custom Build Directory
```bash
git clone --recursive <your-repo-url>
cd yocto-rpi3
source setup-env my-custom-build
bitbake core-image-minimal
```

All approaches will automatically:
- Use the Raspberry Pi 3 machine configuration (`MACHINE = "raspberrypi3"`)
- Apply the cpane custom distribution (`DISTRO = "cpane"`)
- Configure all required layers (raspberrypi, openembedded, cpane)
- Set up systemd as the init system with networking features
- Include essential utilities and development tools

## What's Included

### cpane Distribution Features
- **Target**: Raspberry Pi 3
- **Init System**: systemd with usrmerge
- **Networking**: WiFi, Ethernet, ConnMan, DHCP
- **Utilities**: SSH, nano, htop, wget, curl, rsync
- **Image Format**: SD card image (rpi-sdimg)
- **Serial Console**: 115200 baud on ttyAMA0

### Layer Structure
- `meta-cpane/`: Custom layer with cpane distribution
- `meta-raspberrypi/`: Raspberry Pi BSP layer
- `meta-openembedded/`: Additional OE layers (oe, multimedia, networking, python)
- `poky/`: Core Yocto Project reference distribution

## Building Images

Once the environment is set up:

```bash
# Build minimal image
bitbake core-image-minimal

# Build image with development tools
bitbake core-image-full-cmdline

# Build image with GUI (if supported)
bitbake core-image-sato
```

Images will be created in `build/tmp/deploy/images/raspberrypi3/`.

## Configuration Details

The setup uses Yocto's TEMPLATECONF mechanism to automatically configure builds with the cpane distribution templates, ensuring:
- No manual editing of build configuration files
- Consistent setup across different machines
- Standard Yocto workflow compatibility

### Template Files
- `meta-cpane/conf/templates/cpane/local.conf.sample`: Machine and distribution settings
- `meta-cpane/conf/templates/cpane/bblayers.conf.sample`: Layer configuration

## Customization

To modify the distribution:
1. Edit `meta-cpane/conf/distro/cpane.conf` for distribution-wide changes
2. Update templates in `meta-cpane/conf/templates/cpane/` for build defaults
3. Add recipes to `meta-cpane/recipes-*` directories for additional software

## Development Workflow

```bash
# Set up development environment
source poky/oe-init-build-env

# Make changes to meta-cpane layer
# Build and test
bitbake core-image-minimal

# Deploy to SD card (replace /dev/sdX with your SD card)
sudo dd if=build/tmp/deploy/images/raspberrypi3/core-image-minimal-raspberrypi3.rpi-sdimg of=/dev/sdX bs=1M status=progress
```

## Requirements

- Host system with Yocto Project dependencies installed
- At least 50GB free disk space
- 8GB+ RAM recommended for parallel builds
