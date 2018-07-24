package com.tanavota.tanavota.di.module

import android.content.Context
import com.tanavota.tanavota.model.domain.articledetail.ArticleDetailWebAPI
import com.tanavota.tanavota.model.repository.articledetail.ArticleDetailRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideArticleDetailRepository(context: Context): ArticleDetailRepository {
        return ArticleDetailWebAPI(context)
    }
}