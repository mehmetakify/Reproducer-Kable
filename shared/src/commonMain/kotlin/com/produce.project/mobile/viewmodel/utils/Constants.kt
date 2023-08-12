package com.produce.project.mobile.viewmodel.utils

object Constants {
    const val WRONG_PIN_CODE_TRY = 5
    const val WRONG_PIN_CODE_WAITING_MINUTE = 5

    enum class ProgressState {
        API_ERROR_TIMEOUT,
        API_ERROR_CONNECT,
        API_ERROR
    }
}
