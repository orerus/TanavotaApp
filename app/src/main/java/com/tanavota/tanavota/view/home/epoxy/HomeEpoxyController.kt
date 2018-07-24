package com.tanavota.tanavota.view.home.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.tanavota.tanavota.ArticleThumbnailBindingModel_
import com.tanavota.tanavota.HomeDataLoadingStateBindingModel_
import com.tanavota.tanavota.viewmodel.common.ArticleThumbnailModelable

class HomeEpoxyController : TypedEpoxyController<ArticleThumbnailModelable>() {
    override fun buildModels(data: ArticleThumbnailModelable) {
        data.articleThumbnailList.forEachIndexed { index, articleThumbnail ->
            ArticleThumbnailBindingModel_()
                    .id("article_$index")
                    .viewModel(data)
                    .article(articleThumbnail)
                    .addTo(this)
        }

        if (data.hasNext) {
            HomeDataLoadingStateBindingModel_()
                    .id("data_loading_state")
                    .viewModel(data)
                    .addTo(this)
        }
    }
}