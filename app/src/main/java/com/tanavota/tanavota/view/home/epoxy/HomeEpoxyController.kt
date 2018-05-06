package com.tanavota.tanavota.view.home.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.tanavota.tanavota.ArticleThumbnailBindingModel_
import com.tanavota.tanavota.viewmodel.home.HomeEpoxyModelable
import com.tanavota.tanavota.viewmodel.home.HomeViewModel

class HomeEpoxyController : TypedEpoxyController<HomeEpoxyModelable>() {
    override fun buildModels(data: HomeEpoxyModelable) {
        (data as HomeViewModel).articleThumbnailList.forEachIndexed { index, articleThumbnail ->
            ArticleThumbnailBindingModel_()
                    .id("article_$index")
                    .article(articleThumbnail)
                    .addTo(this)
        }
    }
}