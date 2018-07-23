package com.tanavota.tanavota.di.module

import android.content.Context
import com.tanavota.tanavota.model.domain.articledetail.ArticleDetailWebAPI
import com.tanavota.tanavota.model.domain.home.HomeModel
import com.tanavota.tanavota.model.repository.articledetail.ArticleDetailRepository
import com.tanavota.tanavota.viewmodel.articledetail.ArticleDetailViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class RepositoryModule {

    @Provides
    fun provideArticleDetailRepository(context: Context): ArticleDetailRepository {
        return ArticleDetailWebAPI(context)
    }
}