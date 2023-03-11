package com.tinyDeveloper.na

import android.app.Application
import com.najva.sdk.NajvaClient
import com.najva.sdk.NajvaConfiguration
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NAApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        val config = NajvaConfiguration()
        registerActivityLifecycleCallbacks(NajvaClient.getInstance(this, config))
    }
    override fun onTerminate() {
        super.onTerminate()
        NajvaClient.getInstance().onAppTerminated()
    }
}
