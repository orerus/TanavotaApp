package com.tanavota.tanavota.viewmodel.home

import com.tanavota.tanavota.TanavotaApplication
import com.tanavota.tanavota.di.component.ApplicationComponent
import com.tanavota.tanavota.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [ApplicationModule::class, TestModelModule::class])
@Singleton
interface TestApplicationComponent: ApplicationComponent {
    override fun activityComponent(): TestActivityComponent
    override fun inject(application: TanavotaApplication)
}