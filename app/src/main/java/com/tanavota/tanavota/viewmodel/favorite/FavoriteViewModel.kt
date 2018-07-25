package com.tanavota.tanavota.viewmodel.favorite

import android.databinding.ObservableField
import android.support.v7.widget.RecyclerView
import com.tanavota.tanavota.di.ApplicationComponentStore
import com.tanavota.tanavota.extension.exchange
import com.tanavota.tanavota.extension.getNullable
import com.tanavota.tanavota.model.domain.favorite.FavoriteModel
import com.tanavota.tanavota.model.domain.favorite.FavoriteOperationEvent
import com.tanavota.tanavota.model.domain.home.ArticleThumbnail
import com.tanavota.tanavota.model.domain.home.HomeModel
import com.tanavota.tanavota.util.RecyclerViewScrollListenerDelegate
import com.tanavota.tanavota.viewmodel.common.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference
import javax.inject.Inject


class FavoriteViewModel(delegate: Delegate) :
        HomeModel.Delegate,
        ArticleThumbnailModelable, DataLoadingState.Delegate,
        FavoriteModel.LoadingDelegate, FavoriteModel.OperatingDelegate,
        FavoriteOperator, Disposable {
    interface Delegate {
        fun onInitialLoaded()
        fun onDataLoaded()
        fun onNavigateToDetail(id: String)
        fun onToast(messageId: Int)
    }

    private val wDelegate = WeakReference(delegate)
    @Inject
    lateinit var model: HomeModel
    @Inject
    lateinit var favoriteModel: FavoriteModel
    override val hasNext: Boolean get() = false // ページング未実装
    val initialLoadingState = ObservableField<InitialLoadingState>(InitialLoadingState.Loading)
    override val loadingState = ObservableField<DataLoadingState>(DataLoadingState.Completed)
    override val articleThumbnailList = mutableListOf<ArticleThumbnail>()
    override val favoriteButtonList = mutableListOf<FavoriteButtonModel>()
    val scrollListener = ScrollListener()
    private var disposables = CompositeDisposable()

    init {
        ApplicationComponentStore.get().activityComponent().inject(this)
        subscribeModel()
    }

    fun load() {
        subscribeModelIfNeeded()
        favoriteModel.load()
    }

    fun loadNext() {
        // ページングは未実装
    }

    fun takeIn(event: FavoriteOperationEvent) {
        favoriteModel.takeIn(event)
        updateFavoriteButtonStatus()
    }

    private fun refreshFavoriteButtonList() {
        favoriteButtonList.exchange(articleThumbnailList.map {
            FavoriteButtonModel(it.id, favoriteModel.favoriteArticles.contains(it.id))
        })
    }

    private fun updateFavoriteButtonStatus() {
        favoriteButtonList.forEach {
            it.isFavorite.set(favoriteModel.favoriteArticles.contains(it.articleId))
        }
    }

    private fun removeDeletedArticlesFromFavorite() {
        // 掲載期限切れ等で削除された物件は取得できないので、お気に入りからもまとめて削除する
        val loadedArticleIds = articleThumbnailList.map { it.id }
        val deletedArticleIds = favoriteModel.favoriteArticles.filterNot { loadedArticleIds.contains(it) }
        favoriteModel.remove(deletedArticleIds)
    }

    override fun onInitialLoaded() {
        articleThumbnailList.exchange(model.lastLoadedArticleThumbnailList)
        refreshFavoriteButtonList()
        removeDeletedArticlesFromFavorite()

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

    override fun onFavoriteLoaded() {
        subscribeModelIfNeeded()
        model.loadSpecified(favoriteModel.favoriteArticles)
    }

    override fun onFavoritePushed(operation: FavoriteModel.Operation) {
        updateFavoriteButtonStatus()
        wDelegate.getNullable()?.onToast(operation.messageId)
    }

    override fun onFavoritePushingError(error: FavoriteModel.PushingError) {
        wDelegate.getNullable()?.onToast(error.messageId)
    }

    override fun onFavorite(id: String) {
        subscribeModelIfNeeded()
        favoriteModel.toggle(id)
    }

    override fun onRetry() {
        loadingState.set(DataLoadingState.Loading)
        subscribeModelIfNeeded()
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
            subscribeModel()
        }
    }

    private fun subscribeModel() {
        disposables.addAll(model.subscribe(this))
        disposables.addAll(favoriteModel.subscribe(this as FavoriteModel.LoadingDelegate))
        disposables.addAll(favoriteModel.subscribe(this as FavoriteModel.OperatingDelegate))
    }

    override fun onNavigateToDetail(id: String) {
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