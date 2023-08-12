package com.produce.project.mobile.viewmodel

import com.produce.project.mobile.viewmodel.screens.Level1Navigation
import com.produce.project.mobile.viewmodel.screens.Screen
import com.produce.project.mobile.viewmodel.screens.ScreenInitSettings

typealias URI = String

class ScreenIdentifier private constructor(
    val screen : Screen
) {

    val URI : URI
        get() = returnURI()

    companion object Factory {
        fun get(screen: Screen): ScreenIdentifier {
            return ScreenIdentifier(screen)
        }

    }

    private fun returnURI() : String = screen.asString


    fun getScreenInitSettings(navigation: Navigation) : ScreenInitSettings {
        return screen.initSettings(navigation,this)
    }

    fun level1VerticalBackstackEnabled() : Boolean {
        Level1Navigation.values().forEach {
            if (it.screenIdentifier.URI == this.URI && it.rememberVerticalStack) {
                return true
            }
        }
        return false
    }

}