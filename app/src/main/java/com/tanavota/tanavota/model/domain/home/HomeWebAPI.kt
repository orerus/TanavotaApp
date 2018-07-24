package com.tanavota.tanavota.model.domain.home

import android.content.Context
import com.tanavota.tanavota.model.repository.home.HomeRepository
import com.tanavota.tanavota.model.repository.infrastructure.web.WebAPIClient
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import java.lang.ref.WeakReference

class HomeWebAPI(context: Context) : HomeRepository {
    val wContext = WeakReference(context)

    interface Request {
        @GET("index")
        fun getArticleThumbnails(): Single<Home>

        @GET("index")
        fun getNextArticleThumbnails(
                @Query("page") page: Int
        ): Single<Home>

        @GET("specified")
        fun getSpecifiedThumbnails(
                @Query("ids[]") vararg ids: String
        ): Single<Home>
    }

    override fun home(): Single<Home> {
        return WebAPIClient.getJsonClient(wContext)
                .create(Request::class.java)
                .getArticleThumbnails()
    }

    override fun next(page: Int): Single<Home> {
        return WebAPIClient.getJsonClient(wContext)
                .create(Request::class.java)
                .getNextArticleThumbnails(page)
    }

    override fun specified(ids: List<String>): Single<Home> {
        return WebAPIClient.getJsonClient(wContext)
                .create(Request::class.java)
                .getSpecifiedThumbnails(*ids.toTypedArray())
    }
}