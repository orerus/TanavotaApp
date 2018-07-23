package com.tanavota.tanavota.viewmodel.home

import com.tanavota.tanavota.di.component.ActivityComponent
import com.tanavota.tanavota.di.module.ActivityModule
import com.tanavota.tanavota.di.module.ModelModule
import com.tanavota.tanavota.di.module.RepositoryModule
import com.tanavota.tanavota.di.module.ViewModelModule
import com.tanavota.tanavota.di.scope.PerActivity
import com.tanavota.tanavota.viewmodel.home.HomeViewModel
import dagger.Subcomponent

@Subcomponent(modules = [
    ActivityModule::class,
    TestModelModule::class,
    ViewModelModule::class,
    RepositoryModule::class
])
@PerActivity
interface TestActivityComponent: ActivityComponent {
    override fun inject(viewModel: HomeViewModel)
}