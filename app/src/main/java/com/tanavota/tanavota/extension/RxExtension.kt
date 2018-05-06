package com.tanavota.tanavota.extension

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.subscribeOnIOThread(): Single<T> = subscribeOn(Schedulers.io())
fun <T> Single<T>.subscribeOnMainThread(): Single<T> = subscribeOn(AndroidSchedulers.mainThread())
fun <T> Single<T>.observeOnMainThread(): Single<T> = observeOn(AndroidSchedulers.mainThread())

fun Completable.subscribeOnIOThread(): Completable = subscribeOn(Schedulers.io())
fun Completable.observeOnIOThread(): Completable = observeOn(Schedulers.io())
fun Completable.observeOnMainThread(): Completable = observeOn(AndroidSchedulers.mainThread())

fun <T> Observable<T>.subscribeOnIOThread(): Observable<T> = subscribeOn(Schedulers.io())
fun <T> Observable<T>.observeOnMainThread(): Observable<T> = observeOn(AndroidSchedulers.mainThread())