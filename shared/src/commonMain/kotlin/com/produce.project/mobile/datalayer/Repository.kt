package com.produce.project.mobile.datalayer

import com.produce.project.mobile.datalayer.objects.Auth
import com.produce.project.mobile.datalayer.realm.RealmDatabase

class Repository {

    private val database = RealmDatabase()

    suspend fun removeBackgroundTime() {
        database.saveBackgroundTime(isRemove = true)
    }

    suspend fun setDefaultIsBackground() {
        database.saveBackgroundTime(false, isRemove = false)
    }

    suspend fun saveBackgroundTime(isBackground: Boolean) {
        database.saveBackgroundTime(isBackground, false)
    }

    fun getStartData(): Auth? {
        return database.getStartData()
    }

}
