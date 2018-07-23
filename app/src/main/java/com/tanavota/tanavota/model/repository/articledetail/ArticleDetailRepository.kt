package com.tanavota.tanavota.model.repository.articledetail

import com.tanavota.tanavota.model.domain.articledetail.ArticleDetail
import com.tanavota.tanavota.model.domain.environment.AppEnvironment
import com.tanavota.tanavota.model.repository.home.HomeRepository
import io.reactivex.Single

interface ArticleDetailRepository {
    fun detail(id: String): Single<ArticleDetail>
}