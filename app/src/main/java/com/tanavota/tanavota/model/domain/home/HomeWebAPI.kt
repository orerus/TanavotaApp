package com.tanavota.tanavota.model.domain.home

import com.tanavota.tanavota.model.repository.home.HomeRepository
import com.tanavota.tanavota.model.repository.infrastructure.web.WebAPIClient
import io.reactivex.Single
import retrofit2.http.GET
import java.lang.ref.WeakReference
import android.content.Context

class HomeWebAPI(context: Context) : HomeRepository {
    val wContext = WeakReference(context)

    interface Request {
        @GET("index")
        fun getArticleThumbnails(): Single<Home>
    }

    override fun home(): Single<Home> {
        return WebAPIClient.getJsonClient(wContext)
                .create(Request::class.java)
                .getArticleThumbnails()
    }

}