package com.tanavota.tanavota.viewmodel.home

import com.tanavota.tanavota.di.module.ModelModule
import com.tanavota.tanavota.model.domain.articledetail.ArticleDetailModel
import com.tanavota.tanavota.model.domain.favorite.FavoriteModel
import com.tanavota.tanavota.model.domain.history.HistoryModel
import com.tanavota.tanavota.model.domain.home.HomeModel
import dagger.Module
import dagger.Provides

@Module()
class TestModelModule(
        val mockHomeModel: HomeModel? = null,
        val mockArticleDetailModel: ArticleDetailModel? = null,
        val mockHisotryModel: HistoryModel? = null,
        val mockFavoriteModel: FavoriteModel? = null
) {
    val modelModule = ModelModule()

    @Provides
    fun provideHomeModel(): HomeModel {
        return mockHomeModel ?: modelModule.provideHomeModel()
    }

    @Provides
    fun provideArticleDetailModel(): ArticleDetailModel {
        return mockArticleDetailModel ?: modelModule.provideArticleDetailModel()
    }

    @Provides
    fun provideHistoryModel(): HistoryModel {
        return mockHisotryModel ?: modelModule.provideHistoryModel()
    }

    @Provides
    fun provideFavoriteModel(): FavoriteModel {
        return mockFavoriteModel ?: modelModule.provideFavoriteModel()
    }
}