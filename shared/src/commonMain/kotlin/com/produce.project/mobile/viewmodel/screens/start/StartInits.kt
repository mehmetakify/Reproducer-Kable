package com.produce.project.mobile.viewmodel.screens.start

import com.produce.project.mobile.viewmodel.Navigation
import com.produce.project.mobile.viewmodel.screens.ScreenInitSettings

fun Navigation.getStartData() = ScreenInitSettings (
    title = "Start",
    initState = { StartState() },
    callOnInit = {
    }
)
