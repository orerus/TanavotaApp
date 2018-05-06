package com.tanavota.tanavota.extension

import java.util.*

fun <E> MutableCollection<E>.exchange(elements: Collection<E>): Boolean {
    this.clear()
    return this.addAll(elements)
}