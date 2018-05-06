package com.tanavota.tanavota.viewmodel.home

import android.databinding.ObservableField
import com.tanavota.tanavota.extension.getNullable
import com.tanavota.tanavota.extension.observeOnMainThread
import com.tanavota.tanavota.extension.subscribeOnIOThread
import com.tanavota.tanavota.model.repository.home.HomeRepository
import com.tanavota.tanavota.viewmodel.common.InitialLoadingState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.ref.WeakReference

class HomeViewModel(delegate: Delegate) {
    interface Delegate {
        fun onInitialLoaded()
    }

    private val wDelegate = WeakReference(delegate)
    val initialLoadingState = ObservableField<InitialLoadingState>(InitialLoadingState.Loading)


    fun load() {
        HomeRepository.instance().home()
                .subscribeOnIOThread()
                .observeOnMainThread()
                .subscribe({ home ->
                    initialLoadingState.set(InitialLoadingState.Success)
                    wDelegate.getNullable()?.onInitialLoaded()
                }, { throwable ->
                    
                })

    }
}