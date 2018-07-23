package com.tanavota.tanavota.di.module

import android.app.Application
import android.content.Context
import com.tanavota.tanavota.model.domain.home.HomeModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton



@Module
class ApplicationModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return application
    }
}