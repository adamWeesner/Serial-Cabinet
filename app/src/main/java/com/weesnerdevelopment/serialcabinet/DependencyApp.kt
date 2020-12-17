package com.weesnerdevelopment.serialcabinet

import android.app.Application
import com.weesnerdevelopment.frontendutils.CrashlyticsLogger
import com.weesnerdevelopment.frontendutils.GoogleAnalytics
import dagger.hilt.android.HiltAndroidApp
import kimchi.Kimchi
import kimchi.logger.android.AdbWriter

@HiltAndroidApp
class DependencyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Kimchi.addLog(CrashlyticsLogger())
        Kimchi.addAnalytics(GoogleAnalytics())

        if (BuildConfig.DEBUG) Kimchi.addLog(AdbWriter())
    }
}
