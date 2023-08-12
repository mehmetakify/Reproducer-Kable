package com.produce.project.mobile.viewmodel.utils

sealed class Resource<T> {
    class Success<T>(val data: T? = null) : Resource<T>()
    class Loading<T> : Resource<T>()
    class Error<T>(
        val message: String? = null,
        val errorCode: Int,
        val errorType: Constants.ProgressState? = null
    ) : Resource<T>()
}

