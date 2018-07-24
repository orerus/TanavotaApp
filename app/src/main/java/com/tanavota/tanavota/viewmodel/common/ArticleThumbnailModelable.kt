package com.tanavota.tanavota.viewmodel.common

import android.databinding.ObservableField
import com.tanavota.tanavota.model.domain.home.ArticleThumbnail

interface ArticleThumbnailModelable : DataLoadingState.Delegate {
    val hasNext: Boolean
    val loadingState: ObservableField<DataLoadingState>
    val articleThumbnailList: List<ArticleThumbnail>
    fun onNavigateToDetail(id: String)
}