package com.tanavota.tanavota.model.domain.articledetail

import android.view.View
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.tanavota.tanavota.R

@EpoxyModelClass(layout = R.layout.article_detail_additional_information_border)
abstract class ArticleDetailAdditionalInformationBorder : EpoxyModelWithHolder<ArticleDetailAdditionalInformationBorder.Holder>() {

    class Holder : EpoxyHolder() {
        override fun bindView(itemView: View?) {
            // no operation
        }
    }
}
