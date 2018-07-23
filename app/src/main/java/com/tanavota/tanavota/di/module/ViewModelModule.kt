package com.tanavota.tanavota.di.module

import com.tanavota.tanavota.model.domain.home.HomeModel
import com.tanavota.tanavota.viewmodel.articledetail.ArticleDetailViewModel
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {

    @Provides
    fun provideArticleDetailViewModel(): ArticleDetailViewModel {
        return ArticleDetailViewModel()
    }
}