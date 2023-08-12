package com.produce.project.mobile.viewmodel

import com.produce.project.mobile.datalayer.Repository

fun DKMPViewModel.Factory.getDesktopInstance() : DKMPViewModel {
    val repository = Repository()
    return DKMPViewModel(repository)
}