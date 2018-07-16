package com.tanavota.tanavota.di.component

import com.tanavota.tanavota.di.module.ActivityModule
import com.tanavota.tanavota.di.module.ModelModule
import com.tanavota.tanavota.di.scope.PerActivity
import com.tanavota.tanavota.viewmodel.home.HomeViewModel
import dagger.Subcomponent

@PerActivity
@Subcomponent(modules = [
    ActivityModule::class,
    ModelModule::class
])
interface ActivityComponent {
    fun inject(viewModel: HomeViewModel)
}