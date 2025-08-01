# Raspberry Pi 3 Serial Port Setup for cpane Distro

## Overview

The serial port (UART) on Raspberry Pi 3 has been configured and enabled in your cpane custom distro. This setup uses the primary UART (ttyAMA0) on GPIO pins 14 (TX) and 15 (RX).

## Changes Made

### 1. Distro Configuration (`meta-cpane/conf/distro/cpane.conf`)
- **ENABLE_UART = "1"**: Enables UART functionality
- **SERIAL_CONSOLES = "115200;ttyAMA0"**: Configures serial console at 115200 baud
- **RPI_KERNEL_DEVICETREE_OVERLAY:append = " disable-bt"**: Disables Bluetooth to free up the primary UART
- **RPI_EXTRA_CONFIG:append = '\n# Enable UART\nenable_uart=1\n'**: Adds config.txt entry to enable UART

### 2. Serial Setup Recipe (`meta-cpane/recipes-core/serial-setup/`)
- Created a custom recipe that provides a `serial-test` utility
- Includes dependencies for serial port utilities

### 3. Additional Packages Added
- **minicom**: Terminal emulation program for serial communication
- **screen**: Can be used for serial communication
- **util-linux**: Provides `stty` and other serial utilities

## Hardware Connections

For Raspberry Pi 3, the serial port is available on:
- **GPIO 14 (Pin 8)**: TX (Transmit)
- **GPIO 15 (Pin 10)**: RX (Receive)
- **Ground**: Any ground pin (e.g., Pin 6, 9, 14, 20, 25, 30, 34, 39)

```
Raspberry Pi 3 GPIO Header:
 1  2
 3  4
 5  6  <- Ground
 7  8  <- TX (GPIO 14)
 9 10  <- RX (GPIO 15)
```

## Building and Testing

### 1. Rebuild Your Image
```bash
# From your yocto-rpi3 directory
source setup-env
bitbake core-image-minimal
```

### 2. Flash the New Image
Flash the updated image to your SD card and boot your Raspberry Pi 3.

### 3. Test Serial Port
Once booted, run the serial test utility:
```bash
serial-test
```

This will:
- Check if `/dev/ttyAMA0` exists
- Verify permissions
- Configure the serial port
- Provide usage instructions

### 4. Manual Testing
You can test the serial port manually:

**Configure the port:**
```bash
stty -F /dev/ttyAMA0 115200 cs8 -cstopb -parenb raw -echo
```

**Send data:**
```bash
echo "Hello Serial!" > /dev/ttyAMA0
```

**Read data:**
```bash
cat /dev/ttyAMA0
```

**Using minicom:**
```bash
minicom -D /dev/ttyAMA0 -b 115200
```

**Using screen:**
```bash
screen /dev/ttyAMA0 115200
```

## Troubleshooting

### Device Not Found
If `/dev/ttyAMA0` doesn't exist:
1. Check available devices: `ls -la /dev/tty*`
2. Look for `ttyS0` or `ttyS1` as alternatives
3. Verify kernel has UART support: `dmesg | grep -i uart`

### Permission Issues
If you get permission errors:
```bash
# Add user to dialout group
sudo usermod -a -G dialout cpane
# Then logout and login again
```

### Console Conflicts
If you're using the serial console for login, you may need to:
```bash
# Disable serial console temporarily
sudo systemctl stop serial-getty@ttyAMA0.service
sudo systemctl disable serial-getty@ttyAMA0.service
```

### Bluetooth Interference
The `disable-bt` overlay should handle this, but if you have issues:
- Verify Bluetooth is disabled: `systemctl status bluetooth`
- Check device tree overlays: `vcgencmd get_config int`

## Advanced Configuration

### Multiple UARTs
Raspberry Pi 3 has additional UARTs available via overlays:
- `uart1` overlay enables `/dev/ttyAMA1`
- `uart2` overlay enables `/dev/ttyAMA2`

Add to your distro config if needed:
```
RPI_KERNEL_DEVICETREE_OVERLAY:append = " uart1 uart2"
```

### Different Baud Rates
To use different baud rates, modify the `SERIAL_CONSOLES` setting:
```
SERIAL_CONSOLES = "9600;ttyAMA0"  # For 9600 baud
```

## Hardware Testing Setup

For a complete test, you'll need:
1. **USB-to-TTL Serial Adapter**: Connect to another computer
2. **Jumper Wires**: To connect Pi GPIO to serial adapter
3. **Terminal Software**: PuTTY, minicom, or similar on the other computer

**Connection:**
- Pi GPIO 14 (TX) → Serial Adapter RX
- Pi GPIO 15 (RX) → Serial Adapter TX  
- Pi Ground → Serial Adapter Ground

## What's Working Now

With these changes, your cpane distro will have:
✅ UART enabled in kernel and bootloader
✅ Primary UART available on GPIO 14/15
✅ Bluetooth disabled to avoid conflicts
✅ Serial utilities (minicom, screen) installed
✅ Custom test utility for verification
✅ Proper device permissions and groups
