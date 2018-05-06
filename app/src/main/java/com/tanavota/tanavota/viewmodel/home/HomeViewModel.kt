package com.tanavota.tanavota.viewmodel.home

import android.databinding.ObservableField
import android.support.v7.widget.RecyclerView
import com.tanavota.tanavota.extension.exchange
import com.tanavota.tanavota.extension.getNullable
import com.tanavota.tanavota.extension.observeOnMainThread
import com.tanavota.tanavota.extension.subscribeOnIOThread
import com.tanavota.tanavota.model.domain.home.ArticleThumbnail
import com.tanavota.tanavota.model.domain.home.HomeModel
import com.tanavota.tanavota.model.repository.home.HomeRepository
import com.tanavota.tanavota.util.RecyclerViewScrollListenerDelegate
import com.tanavota.tanavota.viewmodel.common.DataLoadingState
import com.tanavota.tanavota.viewmodel.common.InitialLoadingState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.ref.WeakReference

class HomeViewModel(delegate: Delegate):
        HomeModel.Delegate, HomeEpoxyModelable, DataLoadingState.Delegate {
    interface Delegate {
        fun onInitialLoaded()
        fun onDataLoaded()
    }

    private val wDelegate = WeakReference(delegate)
    private val model = HomeModel()
    val initialLoadingState = ObservableField<InitialLoadingState>(InitialLoadingState.Loading)
    val loadingState = ObservableField<DataLoadingState>(DataLoadingState.Completed)
    val articleThumbnailList = mutableListOf<ArticleThumbnail>()
    val scrollListener = ScrollListener()

    init {
        model.subscribe(this)
    }

    fun load() {
        model.loadInitial()
    }

    fun loadNext() {
        if (model.hasNext) {
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