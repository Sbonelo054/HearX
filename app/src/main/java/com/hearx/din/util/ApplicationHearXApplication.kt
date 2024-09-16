package com.hearx.din.util

import android.app.Application
import com.hearx.din.dependencyinjection.repositoryModule
import com.hearx.din.dependencyinjection.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ApplicationHearXApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@ApplicationHearXApplication)
            modules(repositoryModule,viewModelModule)
        }
    }
}