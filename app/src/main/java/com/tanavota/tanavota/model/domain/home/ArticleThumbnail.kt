package com.tanavota.tanavota.model.domain.home

class ArticleThumbnail(
        val id: String,
        val articleItem: String,
        val location: String,
        val buildingStructure: String,
        val timeOld: String,
        val siteName: String,
        val imageUrl: String,
        val formatPrice: String,
        val formatYield: String
) {
    companion object {
        fun empty(): ArticleThumbnail {
            return ArticleThumbnail(
                    id = "",
                    articleItem = "",
                    location = "",
                    buildingStructure = "",
                    timeOld = "",
                    siteName = "",
                    imageUrl = "",
                    formatYield = "",
                    formatPrice = ""
            )
        }
    }
}