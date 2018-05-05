package com.tanavota.tanavota.model.repository.local

import android.content.Context
import android.content.SharedPreferences
import com.tanavota.tanavota.model.domain.KeyValueStore
import io.reactivex.Completable

abstract class SharedPreferenceBase<in T : Enum<*>> : KeyValueStore<T> {
    lateinit var sharedPreferences: SharedPreferences

    open fun initialize(context: Context) {
        createPreferenceInstance(context)
    }

    /**
     * このメソッド内で親クラスの sharedPreferences を初期化してください。
     */
    abstract fun createPreferenceInstance(context: Context)

    fun commitValueSync(key: String, value: String): Boolean {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        return editor.commit()
    }

    private fun commitBooleanValueSync(key: String, value: Boolean): Boolean {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        return editor.commit()
    }

    override fun commitValueSync(key: T, value: Boolean): Boolean {
        return commitBooleanValueSync(key.name, value)
    }

    override fun commitValueSync(key: T, value: String): Boolean {
        return commitValueSync(key.name, value)
    }

    override fun commitValuesSync(key: T, values: Set<String>): Boolean {
        val editor = sharedPreferences.edit()
        editor.putStringSet(key.name, values)
        return editor.commit()
    }

    override fun commitValue(key: T, value: Boolean): Completable {
        return Completable.create { emitter ->
            val committed = commitValueSync(key, value)
            if (committed) {
                emitter.onComplete()
            } else {
                emitter.onError(PreferenceComitmentError())
            }
        }.retry(3)
    }

    override fun commitValue(key: T, value: String): Completable {
        return Completable.create { emitter ->
            val committed = commitValueSync(key, value)
            if (committed) {
                emitter.onComplete()
            } else {
                emitter.onError(PreferenceComitmentError())
            }
        }.retry(3)
    }

    override fun commitValue(key: String, value: String): Completable {
        return Completable.create { emitter ->
            val committed = commitValueSync(key.toString(), value)
            if (committed) {
                emitter.onComplete()
            } else {
                emitter.onError(PreferenceComitmentError())
            }
        }.retry(3)
    }

    override fun getBooleanValue(key: T): Boolean {
        return sharedPreferences.getBoolean(key.name, false)
    }

    override fun getValue(key: T): String? {
        return sharedPreferences.getString(key.name, null)
    }

    override fun getValues(key: T): Set<String>? {
        return sharedPreferences.getStringSet(key.name, null)
    }

    override fun getValue(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    override fun contains(key: T): Boolean = sharedPreferences.contains(key.name)

    class PreferenceComitmentError : Throwable() {
        override val message: String? = "SharedPreferenceの保存に失敗しました。"
    }
}