package com.tanavota.tanavota.model.repository.infrastructure.web

import android.content.Context
import com.google.gson.GsonBuilder
import com.tanavota.tanavota.extension.getNullable
import com.tanavota.tanavota.model.domain.Server
import com.tanavota.tanavota.model.domain.environment.AppEnvironment
import com.tanavota.tanavota.util.UnsafeOkHttpClient
import okhttp3.Cache
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.ref.WeakReference
import java.net.CookieManager
import java.net.CookieStore
import java.net.HttpCookie
import java.net.URI
import java.util.concurrent.TimeUnit

class WebAPIClient {
    enum class Format {
        JSON;
    }

    companion object {
        private val clientMap = mutableMapOf<Pair<Format, Boolean>, Retrofit>()

        fun initialize() {
            clientMap.clear()
        }

        fun getJsonClient(wContext: WeakReference<Context>): Retrofit {
            return getClient(wContext, Format.JSON)
        }

        private fun getClient(wContext: WeakReference<Context>,
                              format: Format = Format.JSON,
                              withChache: Boolean = false): Retrofit {
            val client = clientMap.getOrPut(Pair(format, withChache), {
                build(wContext, when (format) {
                    Format.JSON -> GsonConverterFactory.create(GsonBuilder().setLenient().create())
                }, withChache)
            })
            return client
        }

        private fun build(wContext: WeakReference<Context>,
                          converterFactory: Converter.Factory,
                          withCache: Boolean = false): Retrofit {
            val server = AppEnvironment.current().server

            fun CookieStore.addSecureHttpCookie(uri: URI, key: String, value: String) {
                val httpCookie = HttpCookie(key, value)
                httpCookie.secure = true
                httpCookie.domain = uri.host
                add(uri, httpCookie)
            }

            val clientBuilder = when (server) {
                Server.Debug -> UnsafeOkHttpClient.getUnsafeOkHttpClient()
                Server.Prod -> OkHttpClient()
                else -> OkHttpClient()
            }.newBuilder()

            clientBuilder.retryOnConnectionFailure(true)
//            clientBuilder.addInterceptor()

            val timeoutValue = 15L
            clientBuilder.connectTimeout(timeoutValue, TimeUnit.SECONDS)
            clientBuilder.readTimeout(timeoutValue, TimeUnit.SECONDS)
            clientBuilder.writeTimeout(timeoutValue, TimeUnit.SECONDS)

            val cookieManager = CookieManager()
            val cookieStore = cookieManager.cookieStore

            // クッキーまわりの制御はここであとでやる.
            /*
            LocalData.getString(LocalDataKey.USER_AKSH)?.let {
                cookieStore.addSecureHttpCookie(URI(Environment.type().baseUrl()), "aksh", it)
            }
            */

            clientBuilder.cookieJar(JavaNetCookieJar(cookieManager))
//            clientBuilder.authenticator(TokenAuthenticator())

            if (withCache) {
                val dir = wContext.getNullable()?.getDir("okcache", Context.MODE_PRIVATE)
                val cacheSize = 10 * 1024 * 1024 // 10 MB
                val cache = Cache(dir, cacheSize.toLong())
                clientBuilder.cache(cache)
            }

            val retrofit = Retrofit.Builder()
                    .baseUrl(server.apiBaseUrl())
                    .addConverterFactory(converterFactory)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(clientBuilder.build())
                    .build()

            return retrofit
        }
    }
}