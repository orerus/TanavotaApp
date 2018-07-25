package com.tanavota.tanavota.di.component

import com.tanavota.tanavota.di.module.ActivityModule
import com.tanavota.tanavota.di.module.ModelModule
import com.tanavota.tanavota.di.module.RepositoryModule
import com.tanavota.tanavota.di.module.ViewModelModule
import com.tanavota.tanavota.di.scope.PerActivity
import com.tanavota.tanavota.model.domain.articledetail.ArticleDetailModel
import com.tanavota.tanavota.model.domain.favorite.FavoriteModel
import com.tanavota.tanavota.view.BaseFragment
import com.tanavota.tanavota.view.articledetail.ArticleDetailFragment
import com.tanavota.tanavota.viewmodel.articledetail.ArticleDetailViewModel
import com.tanavota.tanavota.viewmodel.history.HistoryViewModel
import com.tanavota.tanavota.viewmodel.home.HomeViewModel
import dagger.Subcomponent

@PerActivity
@Subcomponent(modules = [
    ActivityModule::class,
    ModelModule::class,
    ViewModelModule::class,
    RepositoryModule::class
])
interface ActivityComponent {
    fun inject(viewModel: HomeViewModel)
    fun inject(fragment: ArticleDetailFragment)
    fun inject(viewModel: ArticleDetailViewModel)
    fun inject(model: ArticleDetailModel)
    fun inject(model: HistoryViewModel)
    fun inject(fragment: BaseFragment)
    fun inject(model: FavoriteModel)
}