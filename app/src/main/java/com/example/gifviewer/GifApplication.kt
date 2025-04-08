package com.example.gifviewer

import android.app.Application
import com.example.gifviewer.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GifApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@GifApplication)
            modules(appModule)
        }
    }
}