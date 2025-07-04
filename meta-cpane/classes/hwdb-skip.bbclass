# meta-cpane/classes/hwdb-skip.bbclass

python __anonymous__ () {
    from oe.package_manager.rpm.manifest import InterceptScripts
    if "update_udev_hwdb" in InterceptScripts._intercept_hooks:
        del InterceptScripts._intercept_hooks["update_udev_hwdb"]
        bb.note("=== hwdb-skip: update_udev_hwdb intercept removed ===")
}
