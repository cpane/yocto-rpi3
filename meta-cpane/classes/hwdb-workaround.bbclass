# meta-cpane/classes/hwdb-workaround.bbclass

python __anonymous__ () {
    from oe.package_manager.rpm.manifest import InterceptScripts
    bb.note("=== hwdb-workaround.bbclass is being parsed ===")
    bb.note("=== hwdb workaround: intercept hooks before ===")
    bb.note(str(InterceptScripts._intercept_hooks))
    if "update_udev_hwdb" in InterceptScripts._intercept_hooks:
        del InterceptScripts._intercept_hooks["update_udev_hwdb"]
        bb.note("=== hwdb workaround: hook removed ===")
    else:
        bb.note("=== hwdb workaround: hook not found ===")
}

