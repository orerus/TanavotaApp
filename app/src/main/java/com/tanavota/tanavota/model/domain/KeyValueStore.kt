package com.tanavota.tanavota.model.domain

import io.reactivex.Completable

/**
 */
interface KeyValueStore<in T : Enum<*>> {
    fun commitValueSync(key: T, value: Boolean): Boolean
    fun commitValueSync(key: T, value: String): Boolean
    fun commitValuesSync(key: T, values: Set<String>): Boolean
    fun commitValue(key: T, value: Boolean): Completable
    fun commitValue(key: T, value: String): Completable
    fun getValue(key: T): String?
    fun getValues(key: T): Set<String>?
    fun getBooleanValue(key: T): Boolean
    fun commitValue(key: String, value: String): Completable
    fun getValue(key: String): String?
    fun contains(key: T): Boolean
}