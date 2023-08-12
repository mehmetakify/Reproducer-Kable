package com.produce.project.mobile.viewmodel.screens

import com.produce.project.mobile.viewmodel.Navigation
import com.produce.project.mobile.viewmodel.ScreenIdentifier
import com.produce.project.mobile.viewmodel.screens.start.getStartData


enum class Screen(
    val asString: String,
    val navigationLevel: Int = 0,
    val initSettings: Navigation.(ScreenIdentifier) -> ScreenInitSettings,
    val stackableInstances: Boolean = false,
) {
    Start("start", 0, { getStartData() }, true)
}
