package com.produce.project.mobile

import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = ((UIDevice.currentDevice.model + " " +
            UIDevice.currentDevice.systemName() + " " +
            UIDevice.currentDevice.systemVersion) as String).replace("iPad iPad", "iPad")
    override val scanner = null
}

actual fun getPlatform(): Platform = IOSPlatform()
