package com.tanavota.tanavota.model.domain.home

import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import com.tanavota.tanavota.extension.exchange
import com.tanavota.tanavota.extension.observeOnMainThread
import com.tanavota.tanavota.extension.subscribeOnIOThread
import com.tanavota.tanavota.model.repository.home.HomeRepository
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import java.lang.ref.WeakReference

class HomeModel() {
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
    var totalCount = 0
    var page = 1 // Fragmentを復元する場合は保持するように変更すること
    val hasNext get() = articleThumbnailList.count() < totalCount

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
                    totalCount = home.totalCount
                    page++
                    initialLoadingRelay.accept(Unit)
                }, { throwable ->
                    Timber.e(throwable)
                    initialLoadingErrorRelay.accept(Unit)
                })
                .run { disposables.add(this) }
    }

    fun loadNext() {
        if (hasNext) {
            HomeRepository.instance().next(page)
                    .subscribeOnIOThread()
                    .observeOnMainThread()
                    .subscribe({ home: Home ->
                        lastLoadedArticleThumbnailList.exchange(home.articleItems)
                        articleThumbnailList.addAll(home.articleItems)
                        totalCount = home.totalCount
                        page++
                        nextLoadingRelay.accept(Unit)
                    }, { throwable ->
                        Timber.e(throwable)
                        nextLoadingErrorRelay.accept(Unit)
                    })
                    .run { disposables.add(this) }
        } else {
            nextLoadingRelay.accept(Unit)
        }
    }
}