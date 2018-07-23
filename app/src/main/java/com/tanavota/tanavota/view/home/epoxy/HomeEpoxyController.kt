package com.tanavota.tanavota.view.home.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.tanavota.tanavota.ArticleThumbnailBindingModel_
import com.tanavota.tanavota.HomeDataLoadingStateBindingModel_
import com.tanavota.tanavota.viewmodel.home.HomeEpoxyModelable
import com.tanavota.tanavota.viewmodel.home.HomeViewModel

class HomeEpoxyController : TypedEpoxyController<HomeEpoxyModelable>() {
    override fun buildModels(data: HomeEpoxyModelable) {
        val viewModel = data as HomeViewModel
        viewModel.articleThumbnailList.forEachIndexed { index, articleThumbnail ->
            ArticleThumbnailBindingModel_()
                    .id("article_$index")
                    .viewModel(viewModel)
                    .article(articleThumbnail)
                    .addTo(this)
        }

        HomeDataLoadingStateBindingModel_()
                .id("data_loading_state")
                .viewModel(viewModel)
                .addTo(this)
    }
}