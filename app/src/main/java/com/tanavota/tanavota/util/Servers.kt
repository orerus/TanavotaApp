package com.tanavota.tanavota.util

import com.tanavota.tanavota.BuildConfig

/**
 * Created by murata_sho on 2018/03/26.
 */
class Servers {

    fun apiBaseUrl(): String {
        return if (BuildConfig.DEBUG) {
            "tanavota.xyz"
        } else {
            "tanavota.com"
        }
    }

    companion object {

    }
}