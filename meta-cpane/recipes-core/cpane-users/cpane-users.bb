PV = "1.0"
PR = "r1"
SUMMARY = "Add cpane user with sudo access"
DESCRIPTION = "Creates cpane user, assigns to groups, and allows passwordless sudo"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit useradd systemd

USERADD_PACKAGES = "${PN}"
USERADD_PARAM:${PN} = "-d /home/cpane -s /bin/bash -G wheel,dialout,audio,video,users cpane"
GROUPADD_PARAM:${PN} = "-r wheel"

RDEPENDS:${PN} += "sudo bash"

SRC_URI += " \
    file://90-cpane \
    file://set-cpane-password.service \
"
SRC_URI[90-cpane.md5sum] = "a28ba1709db9a9c1e54f3de1bc0952d5"
SRC_URI[90-cpane.sha256sum] = "6b4c40d177f490829fb5f2507d8bb8bcd8767b92f20230e0f95637d601be809d"
SRC_URI[set-cpane-password.service.md5sum] = "c967689333f41c2c61f1fefbd3fb3201"
SRC_URI[set-cpane-password.service.sha256sum] = "51b7638889a7e8592110aa87f88ad75d6a7637ea4c0ff57b248004cb8469d68e"

do_install() {
    # Install sudoers config to a temporary location for postinst script
    install -d ${D}${datadir}/${PN}
    install -m 0440 ${WORKDIR}/90-cpane ${D}${datadir}/${PN}/90-cpane

    # Install systemd service
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/set-cpane-password.service ${D}${systemd_system_unitdir}/set-cpane-password.service

    # Enable the systemd service
    install -d ${D}${sysconfdir}/systemd/system/multi-user.target.wants
    ln -sf ${systemd_system_unitdir}/set-cpane-password.service \
        ${D}${sysconfdir}/systemd/system/multi-user.target.wants/set-cpane-password.service
}

pkg_postinst:${PN}() {
    #!/bin/sh
    # Install sudoers file after sudo package has created the directory
    if [ -d $D${sysconfdir}/sudoers.d ] || [ -d ${sysconfdir}/sudoers.d ]; then
        cp $D${datadir}/${PN}/90-cpane $D${sysconfdir}/sudoers.d/90-cpane 2>/dev/null || \
        cp ${datadir}/${PN}/90-cpane ${sysconfdir}/sudoers.d/90-cpane
        chmod 0440 $D${sysconfdir}/sudoers.d/90-cpane 2>/dev/null || \
        chmod 0440 ${sysconfdir}/sudoers.d/90-cpane
    fi
}

FILES:${PN} += " \
    ${datadir}/${PN}/90-cpane \
    ${systemd_system_unitdir}/set-cpane-password.service \
    ${sysconfdir}/systemd/system/multi-user.target.wants/set-cpane-password.service \
"

INSANE_SKIP:${PN} = "already-stripped ldflags textrel"
