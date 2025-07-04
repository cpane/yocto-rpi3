inherit hwdb-skip

ROOTFS_POSTPROCESS_COMMAND += "ensure_hwdb_dir_exists;"

ensure_hwdb_dir_exists() {
    mkdir -p ${IMAGE_ROOTFS}/usr/lib/udev
}

