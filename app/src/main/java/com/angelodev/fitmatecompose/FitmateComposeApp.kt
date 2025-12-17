package com.angelodev.fitmatecompose

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class FitmateComposeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize any libraries or dependencies here
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        Timber.plant(Timber.DebugTree())

    }
}