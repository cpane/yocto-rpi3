SUMMARY = "Serial port setup and utilities for cpane distro"
DESCRIPTION = "Ensures proper serial port configuration and provides useful serial utilities"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://serial-test"

S = "${WORKDIR}"

RDEPENDS:${PN} = "bash util-linux coreutils"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/serial-test ${D}${bindir}/
}

FILES:${PN} = "${bindir}/serial-test"
