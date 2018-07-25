package com.tanavota.tanavota.viewmodel.articledetail

import android.content.Intent
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.tanavota.tanavota.di.ApplicationComponentStore
import com.tanavota.tanavota.extension.getNullable
import com.tanavota.tanavota.model.domain.articledetail.Article
import com.tanavota.tanavota.model.domain.articledetail.ArticleDetailModel
import com.tanavota.tanavota.model.domain.favorite.FavoriteModel
import com.tanavota.tanavota.model.domain.history.HistoryModel
import com.tanavota.tanavota.view.articledetail.ArticleDetailFragment
import com.tanavota.tanavota.viewmodel.common.FavoriteButtonModel
import com.tanavota.tanavota.viewmodel.common.FavoriteOperator
import com.tanavota.tanavota.viewmodel.common.InitialLoadingState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference
import javax.inject.Inject

class ArticleDetailViewModel : ArticleDetailModel.Delegate, ArticleDetailModelable,
        FavoriteModel.LoadingDelegate, FavoriteModel.OperatingDelegate,
        FavoriteOperator, Disposable {
    interface Delegate {
        fun onInitialLoaded()
        fun onFavoriteLoaded()
        fun onTransfer(intent: Intent)
        fun onToast(messageId: Int)
    }

    private lateinit var wDelegate: WeakReference<Delegate>
    private val handler = Handler()
    private var disposables = CompositeDisposable()
    @Inject
    lateinit var model: ArticleDetailModel
    @Inject
    lateinit var historyModel: HistoryModel
    @Inject
    lateinit var favoriteModel: FavoriteModel
    val initialLoadingState = ObservableField<InitialLoadingState>(InitialLoadingState.Loading)
    var articleId: String = ""
    var article: Article = Article.empty()
    var images: List<String> = listOf()
    val currentImageUrl: ObservableField<String> = ObservableField()
    val maxImageNumberString: ObservableField<String> = ObservableField("0")
    var currentImageNumber: Int = 1
    val currentImageNumberString = ObservableField<String>(currentImageNumber.toString())
    val noImageViewVisibility: ObservableInt = ObservableInt(View.VISIBLE)
    val leftArrowVisibility: ObservableInt = ObservableInt(View.GONE)
    val rightArrowVisibility: ObservableInt = ObservableInt(View.GONE)
    val isEnableLeftArrow: Boolean get() = currentImageNumber > 1
    val isEnableRightArrow: Boolean get() = currentImageNumber < maxImageNumberString.get()?.toInt() ?: 0
    var favoriteButton = ObservableField<FavoriteButtonModel>(FavoriteButtonModel(articleId, false))
    var shouldLoadOnlyFavorite = false

    init {
        ApplicationComponentStore.get().activityComponent().inject(this)
        subscribeModel()
    }

    fun setDelegate(delegate: Delegate) {
        this.wDelegate = WeakReference(delegate)
    }

    fun load() {
        subscribeModelIfNeeded()
        historyModel.push(articleId) // 結果は関知しない
        favoriteModel.load()
    }

    fun favoriteLoad() {
        shouldLoadOnlyFavorite = true
        favoriteModel.load()
    }

    fun createSaveInstanceState(): Bundle {
        return Bundle().apply {
            putString(ArticleDetailFragment.ARTICLE_ID_KEY, articleId)
            putParcelable(ArticleDetailFragment.ARTICLE_KEY, article)
            putStringArrayList(ArticleDetailFragment.IMAGES_KEY, ArrayList(images))
        }
    }

    fun restoreSavedInstanceState(bundle: Bundle) {
        bundle.let {
            this.articleId = it.getString(ArticleDetailFragment.ARTICLE_ID_KEY)
            this.article = it.getParcelable(ArticleDetailFragment.ARTICLE_KEY)
            this.images = it.getStringArrayList(ArticleDetailFragment.IMAGES_KEY).toList()
            this.favoriteButton.set(FavoriteButtonModel(articleId, false))

            initializeImages()
        }
    }

    fun refreshImageStatus() {
        currentImageNumberString.set(currentImageNumber.toString())
        currentImageUrl.set(images[currentImageNumber - 1])
        leftArrowVisibility.set(if (isEnableLeftArrow) View.VISIBLE else View.GONE)
        rightArrowVisibility.set(if (isEnableRightArrow) View.VISIBLE else View.GONE)
    }

    fun onSlideToLeft() {
        handler.post {
            if (isEnableLeftArrow.not()) return@post

            currentImageNumber -= 1
            refreshImageStatus()
        }
    }

    fun onSlideToRight() {
        handler.post {
            if (isEnableRightArrow.not()) return@post

            currentImageNumber += 1
            refreshImageStatus()
        }
    }

    override fun onInitialLoaded() {
        this.article = model.article
        this.images = model.images
        favoriteButton.set(FavoriteButtonModel(articleId, favoriteModel.favoriteArticles.contains(articleId)))

        initializeImages()

        initialLoadingState.set(InitialLoadingState.Success)
        wDelegate.getNullable()?.onInitialLoaded()
    }

    private fun initializeImages() {
        currentImageNumber = 1
        maxImageNumberString.set(this.images.size.toString())
        if (this.images.isNotEmpty()) {
            noImageViewVisibility.set(View.GONE)
            refreshImageStatus()
        } else {
            noImageViewVisibility.set(View.VISIBLE)
        }
    }

    override fun onInitialLoadingError() {
        initialLoadingState.set(InitialLoadingState.Error)
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

    fun onTransfer() {
        val uri = Uri.parse(article.url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        wDelegate.getNullable()?.onTransfer(intent)
    }

    override fun onFavoriteLoaded() {
        if (shouldLoadOnlyFavorite) {
            updateFavoriteButtonStatus()
            initialLoadingState.set(InitialLoadingState.Success)
            wDelegate.getNullable()?.onFavoriteLoaded()
            return
        }

        subscribeModelIfNeeded()
        model.loadInitial(articleId)
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

    private fun updateFavoriteButtonStatus() {
        favoriteButton.get()?.let {
            it.isFavorite.set(favoriteModel.favoriteArticles.contains(it.articleId))
        }
    }

    private fun subscribeModel() {
        disposables.addAll(model.subscribe(this))
        disposables.addAll(favoriteModel.subscribe(this as FavoriteModel.LoadingDelegate))
        disposables.addAll(favoriteModel.subscribe(this as FavoriteModel.OperatingDelegate))
    }
}