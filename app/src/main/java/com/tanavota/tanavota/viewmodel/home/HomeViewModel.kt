package com.tanavota.tanavota.viewmodel.home

import android.databinding.ObservableField
import android.support.v7.widget.RecyclerView
import com.tanavota.tanavota.di.ApplicationComponentStore
import com.tanavota.tanavota.extension.exchange
import com.tanavota.tanavota.extension.getNullable
import com.tanavota.tanavota.model.domain.home.ArticleThumbnail
import com.tanavota.tanavota.model.domain.home.HomeModel
import com.tanavota.tanavota.util.RecyclerViewScrollListenerDelegate
import com.tanavota.tanavota.viewmodel.common.DataLoadingState
import com.tanavota.tanavota.viewmodel.common.InitialLoadingState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference
import javax.inject.Inject


class HomeViewModel(delegate: Delegate) :
        HomeModel.Delegate, HomeEpoxyModelable, DataLoadingState.Delegate, Disposable {
    interface Delegate {
        fun onInitialLoaded()
        fun onDataLoaded()
        fun onNavigateToDetail(id: String)
    }

    private val wDelegate = WeakReference(delegate)
    @Inject
    lateinit var model: HomeModel
    val initialLoadingState = ObservableField<InitialLoadingState>(InitialLoadingState.Loading)
    val loadingState = ObservableField<DataLoadingState>(DataLoadingState.Completed)
    val articleThumbnailList = mutableListOf<ArticleThumbnail>()
    val scrollListener = ScrollListener()
    private var disposables = CompositeDisposable()

    init {
        ApplicationComponentStore.get().activityComponent().inject(this)
        disposables.addAll(model.subscribe(this))
    }

    fun load() {
        subscribeModelIfNeeded()
        model.loadInitial()
    }

    fun loadNext() {
        if (model.hasNext) {
            subscribeModelIfNeeded()
            model.loadNext()
        }
    }

    override fun onInitialLoaded() {
        articleThumbnailList.exchange(model.lastLoadedArticleThumbnailList)
        initialLoadingState.set(InitialLoadingState.Success)
        wDelegate.getNullable()?.onInitialLoaded()
    }

    override fun onInitialLoadingError() {
        initialLoadingState.set(InitialLoadingState.Error)
    }

    override fun onNextLoaded() {
        articleThumbnailList.addAll(model.lastLoadedArticleThumbnailList)
        loadingState.set(DataLoadingState.Completed)
        wDelegate.getNullable()?.onDataLoaded()
    }

    override fun onNextLoadingError() {
        loadingState.set(DataLoadingState.Error)
    }

    override fun onRetry() {
        loadingState.set(DataLoadingState.Loading)
        loadNext()
    }

    override fun isDisposed(): Boolean {
        return disposables.isDisposed
    }

    override fun dispose() {
        model.dispose()
        disposables.dispose()
    }

    private fun subscribeModelIfNeeded() {
        if (disposables.isDisposed) {
            disposables = CompositeDisposable()
            disposables.addAll(model.subscribe(this))
        }
    }

    fun onNavigateToDetail(id: String) {
        wDelegate.getNullable()?.onNavigateToDetail(id)
    }

    // region Scroll Listener
    inner class ScrollListener : RecyclerView.OnScrollListener() {
        val delegate = RecyclerViewScrollListenerDelegate()
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            delegate.handleOnScrollEvent(
                    loadingState.get() ?: DataLoadingState.Loading, recyclerView, dx, dy,
                    {
                        loadingState.set(DataLoadingState.Loading)
                        recyclerView.post { loadNext() }
                    }
            )
        }
    }
    // endregion
}