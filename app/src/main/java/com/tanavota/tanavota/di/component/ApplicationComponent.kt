package com.tanavota.tanavota.di.component

import android.app.Application
import com.tanavota.tanavota.TanavotaApplication
import com.tanavota.tanavota.di.module.ActivityModule
import com.tanavota.tanavota.di.module.ApplicationModule
import com.tanavota.tanavota.di.module.ModelModule
import com.tanavota.tanavota.di.scope.PerActivity
import com.tanavota.tanavota.model.domain.home.HomeModel
import com.tanavota.tanavota.view.BaseFragment
import com.tanavota.tanavota.viewmodel.home.HomeViewModel
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class
])
interface ApplicationComponent {
    fun inject(application: TanavotaApplication)
    fun activityComponent(): ActivityComponent
}

