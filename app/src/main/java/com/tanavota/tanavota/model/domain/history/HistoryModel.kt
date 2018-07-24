package com.tanavota.tanavota.model.domain.history

import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import com.tanavota.tanavota.extension.observeOnMainThread
import com.tanavota.tanavota.extension.subscribeOnIOThread
import com.tanavota.tanavota.model.domain.environment.AppEnvironment
import com.tanavota.tanavota.model.repository.local.PreferenceKey
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber

class HistoryModel : Disposable {
    interface LoadingDelegate {
        fun onHistoryLoaded()
    }

    interface PushingDelegate {
        fun onHistoryPushed()
        fun onHistoryPushingError()
    }

    private var disposables: CompositeDisposable = CompositeDisposable()
    val loadingRelay: Relay<Unit> = PublishRelay.create<Unit>().toSerialized()
    val pushingRelay: Relay<Unit> = PublishRelay.create<Unit>().toSerialized()
    val pushingErrorRelay: Relay<Unit> = PublishRelay.create<Unit>().toSerialized()
    var history: ArrayList<String> = arrayListOf()

    fun subscribe(delegate: LoadingDelegate): CompositeDisposable {
        return CompositeDisposable().apply {
            add(loadingRelay.subscribe { delegate.onHistoryLoaded() })
        }
    }

    fun subscribe(delegate: PushingDelegate): CompositeDisposable {
        return CompositeDisposable().apply {
            add(pushingRelay.subscribe { delegate.onHistoryPushed() })
            add(pushingErrorRelay.subscribe { delegate.onHistoryPushingError() })
        }
    }

    fun load() {
        history = AppEnvironment.current().preference.getValuesList(PreferenceKey.History)
                ?: arrayListOf()
        loadingRelay.accept(Unit)
    }

    fun push(id: String) {
        load()

        if (history.contains(id)) history.remove(id)
        history.add(id)
        if (history.size > MAX_HISTORY_NUMBER) history.removeAt(0)

        resetDisposablesIfNeeded()
        AppEnvironment.current().preference.commitValues(PreferenceKey.History, history)
                .subscribeOnIOThread()
                .observeOnMainThread()
                .subscribe({
                    pushingRelay.accept(Unit)
                }, { throwable ->
                    Timber.e(throwable)
                    pushingErrorRelay.accept(Unit)
                })
                .run { disposables.add(this) }
    }

    override fun isDisposed(): Boolean {
        return disposables.isDisposed
    }

    override fun dispose() {
        disposables.dispose()
    }

    private fun resetDisposablesIfNeeded() {
        if (disposables.isDisposed) {
            disposables = CompositeDisposable()
        }
    }

    companion object {
        const val MAX_HISTORY_NUMBER = 20
    }
}