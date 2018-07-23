package com.tanavota.tanavota.model.domain.articledetail

import com.tanavota.tanavota.model.repository.articledetail.ArticleDetailRepository
import com.tanavota.tanavota.model.repository.infrastructure.web.WebAPIClient
import io.reactivex.Single
import retrofit2.http.GET
import java.lang.ref.WeakReference
import android.content.Context
import retrofit2.http.Path
import retrofit2.http.Query


class ArticleDetailWebAPI(context: Context) : ArticleDetailRepository {
        val wContext = WeakReference(context)

    interface Request {
        @GET("detail/{id}")
        fun getArticleDetail(
                @Path("id") id: String
        ): Single<ArticleDetail>
    }

    override fun detail(id: String): Single<ArticleDetail> {
        return WebAPIClient.getJsonClient(wContext)
        .create(Request::class.java)
        .getArticleDetail(id)
    }
}
