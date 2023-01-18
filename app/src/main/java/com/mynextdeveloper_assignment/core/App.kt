package com.mynextdeveloper_assignment.core

import android.app.Application
import android.content.Context
import com.mynextdeveloper_assignment.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import androidx.multidex.MultiDex

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this.applicationContext

        MultiDex.install(this)

        startKoin {
            androidLogger(org.koin.core.logger.Level.DEBUG)
            androidContext(this@App)
            modules(listOf(AppModule))
        }
    }

    companion object {
        lateinit var appContext: Context
    }
}