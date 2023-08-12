package com.produce.project.mobile.viewmodel

import android.app.AlertDialog
import com.produce.project.mobile.datalayer.Repository


fun DKMPViewModel.Factory.getAndroidInstance() : DKMPViewModel {
    val repository = Repository()
    return DKMPViewModel(repository)
}

fun DKMPViewModel.getDefaultAppState() : AppState = AppState()