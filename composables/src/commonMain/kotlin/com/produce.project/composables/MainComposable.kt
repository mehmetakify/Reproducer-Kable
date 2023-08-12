package com.produce.project.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.produce.project.composables.navigation.Router
import com.produce.project.mobile.viewmodel.DKMPViewModel

@Composable
fun MainComposable(model: DKMPViewModel) {
    val appState by model.stateFlow.collectAsState()
    appState.getNavigation(model).Router(model)
}

