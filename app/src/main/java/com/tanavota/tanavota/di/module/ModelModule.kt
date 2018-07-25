package com.tanavota.tanavota.di.module

import com.tanavota.tanavota.model.domain.articledetail.ArticleDetailModel
import com.tanavota.tanavota.model.domain.favorite.FavoriteModel
import com.tanavota.tanavota.model.domain.history.HistoryModel
import com.tanavota.tanavota.model.domain.home.HomeModel
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

    @Provides
    open fun provideHistoryModel(): HistoryModel {
        return HistoryModel()
    }

    @Provides
    open fun provideFavoriteModel(): FavoriteModel {
        return FavoriteModel()
    }
}