package com.produce.project.mobile.viewmodel.screens

import com.produce.project.mobile.viewmodel.ScreenIdentifier

object navigationSettings {
    val startScreen = Level1Navigation.Start
    val homeScreen = Level1Navigation.Start
}

enum class Level1Navigation(val screenIdentifier: ScreenIdentifier, val rememberVerticalStack: Boolean = false) {
    Start( ScreenIdentifier.get(Screen.Start), true)
}