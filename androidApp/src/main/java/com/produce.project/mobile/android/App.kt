package com.produce.project.mobile.android

import android.app.Application
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.produce.project.mobile.viewmodel.DKMPViewModel
import com.produce.project.mobile.viewmodel.getAndroidInstance

class Produce : Application() {

    lateinit var model: DKMPViewModel

    override fun onCreate() {
        super.onCreate()
        model = DKMPViewModel.Factory.getAndroidInstance()
        
        val appLifecycleObserver = AppLifecycleObserver(model)
        ProcessLifecycleOwner.get().lifecycle.addObserver(appLifecycleObserver)
    }
}

class AppLifecycleObserver (private val model: DKMPViewModel) : DefaultLifecycleObserver {

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        model.navigation.onReEnterForeground(true)
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        model.navigation.onEnterBackground(true)
    }

}