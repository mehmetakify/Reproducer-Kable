package com.produce.project.mobile.viewmodel.screens

import com.produce.project.mobile.viewmodel.ScreenIdentifier
import com.produce.project.mobile.viewmodel.ScreenState
import com.produce.project.mobile.viewmodel.StateManager

class ScreenInitSettings (
    val title : String,
    val initState : ((ScreenIdentifier) -> ScreenState?) = { null },
    val callOnInit : suspend (StateManager) -> Unit,
    val reinitOnEachNavigation : Boolean = false,
    val callOnInitAlsoAfterBackground : Boolean = false
)