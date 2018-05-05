package com.tanavota.tanavota.viewmodel.home

import com.tanavota.tanavota.model.repository.home.HomeRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.ref.WeakReference

class HomeViewModel(delegate: Delegate) {
    interface Delegate {
        fun onInitialLoaded()
    }

    private val wDelegate = WeakReference(delegate)

    fun load() {
        HomeRepository.instance().home()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ home ->

                }, { throwable ->
                    
                })

    }
}