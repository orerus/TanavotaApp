package com.tanavota.tanavota

import android.app.Application
import android.util.Log
import com.crashlytics.android.Crashlytics
import com.tanavota.tanavota.model.domain.environment.AppEnvironment
import io.fabric.sdk.android.Fabric
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
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

        AppEnvironment.initialize(this)

        // http://in.fablic.co.jp/entry/2017/04/27/110000
        RxJavaPlugins.setErrorHandler { e ->
            val rootException = if (e is UndeliverableException) e.cause else e
            Timber.e("Undeliverable exception: ", rootException?.message)
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