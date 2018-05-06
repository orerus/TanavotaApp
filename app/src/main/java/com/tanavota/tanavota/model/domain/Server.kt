package com.tanavota.tanavota.model.domain

import com.tanavota.tanavota.BuildConfig

enum class Server {
    Debug, Prod;

    fun isProduction(): Boolean = when (this) {
        Prod -> true
        else -> false
    }

    fun apiBaseUrl(): String = when (this) {
        Debug -> "https://tanavota.com/api/"
        Prod -> "https://tanavota.com/api/"
    }

    fun webBaseUrl(): String = when (this) {
        Debug -> "https://tanavota.com"
        Prod -> "https://tanavota.com"
    }

    companion object {
        val current: Server
            get() = when (BuildConfig.BUILD_TYPE) {
                "release" -> Server.Prod
                "debug" -> Server.Debug
                else -> throw IllegalArgumentException("ビルドタイプが不正です。")
            }
    }
}