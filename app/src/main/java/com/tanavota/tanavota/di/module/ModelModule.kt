package com.tanavota.tanavota.di.module

import com.tanavota.tanavota.di.scope.PerActivity
import com.tanavota.tanavota.model.domain.articledetail.ArticleDetailModel
import com.tanavota.tanavota.model.domain.home.HomeModel
import com.tanavota.tanavota.viewmodel.articledetail.ArticleDetailViewModel
import dagger.Module
import dagger.Provides

@Module
open class ModelModule {

    @Provides
    open fun provideHomeModel(): HomeModel {
        return HomeModel()
    }

    @Provides
    open fun provideArticleDetailModel(): ArticleDetailModel {
        return ArticleDetailModel()
    }
}