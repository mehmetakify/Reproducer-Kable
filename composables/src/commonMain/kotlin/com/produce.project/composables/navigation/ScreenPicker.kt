package com.produce.project.composables.navigation

import androidx.compose.runtime.Composable
import com.produce.project.composables.screens.*
import com.produce.project.mobile.viewmodel.Navigation
import com.produce.project.mobile.viewmodel.ScreenIdentifier
import com.produce.project.mobile.viewmodel.screens.Screen.*
import com.produce.project.mobile.viewmodel.screens.start.*

@Composable
fun Navigation.ScreenPicker(
    screenIdentifier: ScreenIdentifier
) {


    when (screenIdentifier.screen) {

        Start -> {
            (stateProvider.get(screenIdentifier) as StartState).LoginScreen(
            )
        }


        else -> {}
    }

}