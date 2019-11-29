package com.keerthi.currencyconverter

import android.app.Application
import com.facebook.stetho.Stetho
import timber.log.Timber

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this);
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            // Release reporting
        }
    }
}