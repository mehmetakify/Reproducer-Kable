package com.produce.project.composables.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import com.produce.project.composables.navigation.templates.OnePane
import com.produce.project.mobile.viewmodel.*

@Composable
fun Navigation.Router(model: DKMPViewModel) {

    val screenUIsStateHolder = rememberSaveableStateHolder()

    OnePane(screenUIsStateHolder, model)

    screenStatesToRemove.forEach {
        screenUIsStateHolder.removeState(it.URI)
    }
}