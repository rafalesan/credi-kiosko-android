package com.rafalesan.credikiosko

import android.app.Application
import com.rafalesan.credikiosko.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        setup()
    }

    private fun setup() {
        setupTimber()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MainApp)
            modules(appModule,
                    retrofitModule,
                    dataStoreModule,
                    apiModule,
                    datasourceModule,
                    repositoryModule,
                    useCaseModule)
        }
    }

    private fun setupTimber() {
        Timber.plant(Timber.DebugTree())
    }

}