package com.radhecodes.cbctest

import android.app.Application
import com.radhecodes.cbctest.di.myAppModule
import com.radhecodes.cbctest.di.newsViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules (myAppModule, newsViewModelModule)
        }
    }
}