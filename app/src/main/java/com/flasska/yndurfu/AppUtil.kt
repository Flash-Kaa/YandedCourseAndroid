package com.flasska.yndurfu

import android.content.Context
import com.flasska.yndurfu.di.AppComponent

internal val Context.appComponent: AppComponent
    get() = when (this) {
        is YndApp -> this.appComponent
        else -> this.applicationContext.appComponent
    }
