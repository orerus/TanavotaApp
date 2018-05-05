package com.tanavota.tanavota.extension

import java.lang.ref.WeakReference

/**
 * Created by murata_sho on 2018/03/26.
 */
fun <T> WeakReference<T>.getNullable(): T? {
    return get()
}