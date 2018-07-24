package com.tanavota.tanavota.model.domain.articledetail

import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import com.tanavota.tanavota.di.ApplicationComponentStore
import com.tanavota.tanavota.extension.observeOnMainThread
import com.tanavota.tanavota.extension.subscribeOnIOThread
import com.tanavota.tanavota.model.repository.articledetail.ArticleDetailRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject

class ArticleDetailModel : Disposable {
    interface Delegate {
        fun onInitialLoaded()
        fun onInitialLoadingError()
    }

    private var disposables: CompositeDisposable = CompositeDisposable()
    @Inject
    lateinit var articleDetailRepository: ArticleDetailRepository
    val initialLoadingRelay: Relay<Unit> = PublishRelay.create<Unit>().toSerialized()
    val initialLoadingErrorRelay: Relay<Unit> = PublishRelay.create<Unit>().toSerialized()
    var article: Article = Article.empty()
    var images: List<String> = listOf()

    init {
        ApplicationComponentStore.get().activityComponent().inject(this)
    }

    fun subscribe(delegate: Delegate): CompositeDisposable {
        return CompositeDisposable().apply {
            add(initialLoadingRelay.subscribe { delegate.onInitialLoaded() })
            add(initialLoadingErrorRelay.subscribe { delegate.onInitialLoadingError() })
        }
    }

    fun loadInitial(id: String) {
        resetDisposablesIfNeeded()
        articleDetailRepository.detail(id)
                .subscribeOnIOThread()
                .observeOnMainThread()
                .subscribe({ articleDetail: ArticleDetail ->
                    article = articleDetail.article
                    images = articleDetail.images
                    initialLoadingRelay.accept(Unit)
                }, { throwable ->
                    Timber.e(throwable)
                    initialLoadingErrorRelay.accept(Unit)
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
}