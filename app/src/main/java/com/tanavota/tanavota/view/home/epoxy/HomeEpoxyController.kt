package com.tanavota.tanavota.view.home.epoxy

import com.airbnb.epoxy.Typed2EpoxyController
import com.tanavota.tanavota.ArticleThumbnailBindingModel_
import com.tanavota.tanavota.HomeDataLoadingStateBindingModel_
import com.tanavota.tanavota.viewmodel.common.ArticleThumbnailModelable
import com.tanavota.tanavota.viewmodel.common.FavoriteOperator

class HomeEpoxyController : Typed2EpoxyController<ArticleThumbnailModelable, FavoriteOperator>() {
    override fun buildModels(data: ArticleThumbnailModelable, operator: FavoriteOperator) {
        data.articleThumbnailList.forEachIndexed { index, articleThumbnail ->
            ArticleThumbnailBindingModel_()
                    .id("article_$index")
                    .viewModel(data)
                    .article(articleThumbnail)
                    .favoriteViewModel(data.favoriteButtonList.first { articleThumbnail.id == it.articleId })
                    .operator(operator)
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