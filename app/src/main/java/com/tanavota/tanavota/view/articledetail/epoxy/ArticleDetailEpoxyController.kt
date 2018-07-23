package com.tanavota.tanavota.view.articledetail.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.tanavota.tanavota.ArticleDetailBasicInformationBindingModel_
import com.tanavota.tanavota.ArticleDetailImagesBindingModel_
import com.tanavota.tanavota.ArticleDetailTitleBindingModel_
import com.tanavota.tanavota.ArticleDetailTransferButtonBindingModel_
import com.tanavota.tanavota.viewmodel.articledetail.ArticleDetailModelable
import com.tanavota.tanavota.viewmodel.articledetail.ArticleDetailViewModel

class ArticleDetailEpoxyController : TypedEpoxyController<ArticleDetailModelable>() {
    override fun buildModels(data: ArticleDetailModelable?) {
        val viewModel = data as? ArticleDetailViewModel ?: return
        val id = viewModel.articleId

        ArticleDetailTitleBindingModel_()
                .id("${id}_title")
                .article(viewModel.article)
                .addTo(this)

        ArticleDetailBasicInformationBindingModel_()
                .id("${id}_basic_information")
                .article(viewModel.article)
                .addTo(this)

        ArticleDetailTransferButtonBindingModel_()
                .id("${id}_transfer_button_1")
                .viewModel(viewModel)
                .addTo(this)

        ArticleDetailImagesBindingModel_()
                .id("${id}_images")
                .viewModel(viewModel)
                .addTo(this)


    }
}