package com.tanavota.tanavota.di.module

import com.tanavota.tanavota.di.scope.PerActivity
import com.tanavota.tanavota.model.domain.home.HomeModel
import dagger.Module
import dagger.Provides

@Module
class ModelModule {

    @Provides
    fun provideHomeModel(): HomeModel {
        return HomeModel()
    }
}