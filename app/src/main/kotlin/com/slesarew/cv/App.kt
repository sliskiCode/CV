package com.slesarew.cv

import android.app.Application
import com.slesarew.cv.cvscreen.di.cvModule
import com.slesarew.cv.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)

            modules(
                listOf(
                    appModule,
                    cvModule
                )
            )
        }
    }
}