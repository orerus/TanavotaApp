package com.tanavota.tanavota.viewmodel.home

import android.databinding.ObservableField
import com.tanavota.tanavota.extension.exchange
import com.tanavota.tanavota.extension.getNullable
import com.tanavota.tanavota.extension.observeOnMainThread
import com.tanavota.tanavota.extension.subscribeOnIOThread
import com.tanavota.tanavota.model.domain.home.ArticleThumbnail
import com.tanavota.tanavota.model.domain.home.HomeModel
import com.tanavota.tanavota.model.repository.home.HomeRepository
import com.tanavota.tanavota.viewmodel.common.InitialLoadingState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.ref.WeakReference

class HomeViewModel(delegate: Delegate): HomeModel.Delegate {
    interface Delegate {
        fun onInitialLoaded()
    }

    private val wDelegate = WeakReference(delegate)
    private val model = HomeModel()
    val initialLoadingState = ObservableField<InitialLoadingState>(InitialLoadingState.Loading)
    val articleThumbnailList = mutableListOf<ArticleThumbnail>()
    var sample = ObservableField<ArticleThumbnail>(ArticleThumbnail.empty())

    init {
        model.subscribe(this)
    }

    fun load() {
        model.loadInitial()
    }

    override fun onInitialLoaded() {
        articleThumbnailList.exchange(model.lastLoadedArticleThumbnailList)
        sample.set(articleThumbnailList[0])
        initialLoadingState.set(InitialLoadingState.Success)
        wDelegate.getNullable()?.onInitialLoaded()
    }

    override fun onInitialLoadingError() {
        initialLoadingState.set(InitialLoadingState.Error)
    }

    override fun onNextLoaded() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onNextLoadingError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}