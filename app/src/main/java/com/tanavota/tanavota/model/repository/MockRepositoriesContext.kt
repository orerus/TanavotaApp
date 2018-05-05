package com.tanavota.tanavota.model.repository

import com.tanavota.tanavota.model.domain.home.ArticleThumbnail
import com.tanavota.tanavota.model.domain.home.Home
import com.tanavota.tanavota.model.repository.home.HomeRepository
import io.reactivex.Single

class MockRepositoriesContext : RepositoriesContext {
    override val homeRepository: HomeRepository

    constructor(
            homeRepo: HomeRepository = mockHomeRepository()
    ) {
        this.homeRepository = homeRepo
    }

    companion object {
        fun mockHomeRepository() = object : HomeRepository {
            override fun home(): Single<Home> {
                return Single.just(
                        Home(
                                totalCount = 20,
                                articleItems = listOf(
                                        ArticleThumbnail(
                                                id = "883474",
                                                articleItem = "マンション(一棟)",
                                                location = "東京都杉並区光圓寺北1丁目",
                                                buildingStructure = "RC造4階建て",
                                                timeOld = "1972年11月",
                                                siteName = "不動産投資サイト「ノムコム・プロ」",
                                                imageUrl = "https://s3-ap-northeast-1.amazonaws.com/img1.tanavota.com/883474/1.jpg",
                                                formatPrice = "1億2,800万円",
                                                formatYield = "7.3%"
                                        ),
                                        ArticleThumbnail(
                                                id = "883478",
                                                articleItem = "マンション(一棟)",
                                                location = "東京都調布市多摩川5丁目",
                                                buildingStructure = "RC造4階建て",
                                                timeOld = "1989年5月",
                                                siteName = "不動産投資サイト「ノムコム・プロ」",
                                                imageUrl = "https://s3-ap-northeast-1.amazonaws.com/img1.tanavota.com/883478/1.jpg",
                                                formatPrice = "1億5,000万円",
                                                formatYield = "7.01%"
                                        ),
                                        ArticleThumbnail(
                                                id = "883479",
                                                articleItem = "一戸建て",
                                                location = "東京都大田区西蒲田8丁目",
                                                buildingStructure = "鉄骨造4階建て",
                                                timeOld = "1989年5月",
                                                siteName = "不動産投資サイト「ノムコム・プロ」",
                                                imageUrl = "https://s3-ap-northeast-1.amazonaws.com/img1.tanavota.com/883479/1.jpg",
                                                formatPrice = "1億1,500万円",
                                                formatYield = "-"
                                        )
                                )
                        )
                )
            }
        }
    }
}