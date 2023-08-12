package com.produce.project.mobile.datalayer.objects

class Response<T> constructor(
    body: T?, errorCode: Int?, extras: String? = null
) {
    private val body: T?
    private val errorCode: Int?
    private val extras: String?

    init {
        this.body = body
        this.errorCode = errorCode
        this.extras = extras
    }

    fun errorBody(): Int? = errorCode


}