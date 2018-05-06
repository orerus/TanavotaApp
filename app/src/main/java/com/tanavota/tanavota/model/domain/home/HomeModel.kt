package com.tanavota.tanavota.model.domain.home

import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import com.tanavota.tanavota.extension.exchange
import com.tanavota.tanavota.extension.observeOnMainThread
import com.tanavota.tanavota.extension.subscribeOnIOThread
import com.tanavota.tanavota.model.repository.home.HomeRepository
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class HomeModel {
    interface Delegate {
        fun onInitialLoaded()
        fun onInitialLoadingError()
        fun onNextLoaded()
        fun onNextLoadingError()
    }

    private val disposables: CompositeDisposable = CompositeDisposable()
    val initialLoadingRelay: Relay<Unit> = PublishRelay.create<Unit>().toSerialized()
    val initialLoadingErrorRelay: Relay<Unit> = PublishRelay.create<Unit>().toSerialized()
    val nextLoadingRelay: Relay<Unit> = PublishRelay.create<Unit>().toSerialized()
    val nextLoadingErrorRelay: Relay<Unit> = PublishRelay.create<Unit>().toSerialized()
    val articleThumbnailList = mutableListOf<ArticleThumbnail>()
    val lastLoadedArticleThumbnailList = mutableListOf<ArticleThumbnail>()

    fun subscribe(delegate: Delegate): CompositeDisposable {
        return CompositeDisposable().apply {
            add(initialLoadingRelay.subscribe { delegate.onInitialLoaded() })
            add(initialLoadingErrorRelay.subscribe { delegate.onInitialLoadingError() })
            add(nextLoadingRelay.subscribe { delegate.onNextLoaded() })
            add(nextLoadingErrorRelay.subscribe { delegate.onNextLoadingError() })
        }
    }

    fun loadInitial() {
        HomeRepository.instance().home()
                .subscribeOnIOThread()
                .observeOnMainThread()
                .subscribe({ home: Home ->
                    lastLoadedArticleThumbnailList.exchange(home.articleItems)
                    articleThumbnailList.exchange(home.articleItems)
                    initialLoadingRelay.accept(Unit)
                }, { throwable ->
                    Timber.e(throwable)
                    initialLoadingErrorRelay.accept(Unit)
                })
                .run { disposables.add(this) }
    }
}