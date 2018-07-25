package com.tanavota.tanavota.model.domain.favorite

import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import com.tanavota.tanavota.R
import com.tanavota.tanavota.extension.observeOnMainThread
import com.tanavota.tanavota.extension.subscribeOnIOThread
import com.tanavota.tanavota.model.domain.environment.AppEnvironment
import com.tanavota.tanavota.model.repository.local.PreferenceKey
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber

class FavoriteModel : Disposable {
    enum class PushingError(val messageId: Int) {
        MaxNumber(R.string.max_favorite_number_error),
        AddingError(R.string.failed_to_add_favorite),
        RemovingError(R.string.failed_to_remove_favorite)
    }

    enum class Operation(val messageId: Int) {
        Add(R.string.complete_to_add_favorite),
        Remove(R.string.complete_to_remove_favorite)
    }

    interface LoadingDelegate {
        fun onFavoriteLoaded()
    }

    interface OperatingDelegate {
        fun onFavoritePushed(operation: Operation)
        fun onFavoritePushingError(error: PushingError)
    }

    private var disposables: CompositeDisposable = CompositeDisposable()
    val loadingRelay: Relay<Unit> = PublishRelay.create<Unit>().toSerialized()
    val pushingRelay: Relay<Operation> = PublishRelay.create<Operation>().toSerialized()
    val pushingErrorRelay: Relay<PushingError> = PublishRelay.create<PushingError>().toSerialized()
    var favoriteArticles: ArrayList<String> = arrayListOf()

    fun subscribe(delegate: LoadingDelegate): CompositeDisposable {
        return CompositeDisposable().apply {
            add(loadingRelay.subscribe { delegate.onFavoriteLoaded() })
        }
    }

    fun subscribe(delegate: OperatingDelegate): CompositeDisposable {
        return CompositeDisposable().apply {
            add(pushingRelay.subscribe { delegate.onFavoritePushed(it) })
            add(pushingErrorRelay.subscribe { delegate.onFavoritePushingError(it) })
        }
    }

    fun load() {
        loadFromPreference()
        loadingRelay.accept(Unit)
    }

    private fun loadFromPreference() {
        favoriteArticles = AppEnvironment.current().preference.getValuesList(PreferenceKey.Favorite)
                ?: arrayListOf()
    }

    fun toggle(id: String) {
        loadFromPreference()
        var operation = Operation.Add

        if (favoriteArticles.contains(id)) {
            favoriteArticles.remove(id)
            operation = Operation.Remove
        } else {
            if (favoriteArticles.size + 1 > MAX_NUMBER) {
                pushingErrorRelay.accept(PushingError.MaxNumber)
                return
            }
            favoriteArticles.add(id)
        }

        resetDisposablesIfNeeded()
        AppEnvironment.current().preference.commitValues(PreferenceKey.Favorite, favoriteArticles)
                .subscribeOnIOThread()
                .observeOnMainThread()
                .subscribe({
                    if (operation == Operation.Add) {
                        favoriteArticles.add(id)
                    } else {
                        favoriteArticles.remove(id)
                    }
                    pushingRelay.accept(operation)
                }, { throwable ->
                    Timber.e(throwable)
                    pushingErrorRelay.accept(
                            if (operation == Operation.Add) PushingError.AddingError else PushingError.RemovingError
                    )
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
        const val MAX_NUMBER = 20
    }
}