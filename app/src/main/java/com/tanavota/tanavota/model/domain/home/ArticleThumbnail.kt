package com.tanavota.tanavota.model.domain.home

class ArticleThumbnail(
        val id: String,
        val article_item: String,
        val location: String,
        val building_structure: String,
        val time_old: String,
        val siteName: String,
        val imageUrl: String,
        val formatPrice: String,
        val formatYield: String
) {
    val buildingDetail get() = "$building_structure / $time_old"

    companion object {
        fun empty(): ArticleThumbnail {
            return ArticleThumbnail(
                    id = "",
                    article_item = "",
                    location = "",
                    building_structure = "",
                    time_old = "",
                    siteName = "",
                    imageUrl = "",
                    formatYield = "",
                    formatPrice = ""
            )
        }
    }
}