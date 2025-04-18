package com.flasska.yndurfu

import android.app.Application
import com.flasska.yndurfu.di.AppComponent
import com.flasska.yndurfu.di.DaggerAppComponent

internal class YndApp: Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .context(this)
            .build()
    }
}
