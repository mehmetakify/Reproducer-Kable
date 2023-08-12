package com.produce.project.mobile

class DesktopPlatform: Platform {
    override val name: String = "Desktop"
    override val scanner = null
}

actual fun getPlatform(): Platform = DesktopPlatform()