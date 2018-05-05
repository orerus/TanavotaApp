package com.tanavota.tanavota

import android.app.Application
import android.util.Log
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import timber.log.Timber

/**
 * Created by murata_sho on 2018/03/26.
 */
class TanavotaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Crashlytics())

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashReportTree())
        }
    }

    private class CrashReportTree() : Timber.Tree() {

        private class LogException(message: String) : Exception(message)

        override fun log(priority: Int, tag: String?, message: String?, t: Throwable?) {
            when (priority) {
                Log.WARN -> {
                    Crashlytics.logException(LogException(message ?: "message is null."))
                }
                Log.ERROR -> {
                    if (t == null) {
                        Crashlytics.logException(LogException(message ?: "message is null."))
                    } else {
                        Crashlytics.logException(t)
                    }
                }
                else -> {
                    return
                }
            }
        }
    }
}