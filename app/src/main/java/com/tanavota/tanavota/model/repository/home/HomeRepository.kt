package com.tanavota.tanavota.model.repository.home

import com.tanavota.tanavota.model.domain.environment.AppEnvironment
import com.tanavota.tanavota.model.domain.home.Home
import io.reactivex.Single

interface HomeRepository {
    fun home(): Single<Home>

    fun next(page: Int): Single<Home>

    fun specified(ids: List<String>): Single<Home>

    companion object {
        fun instance(): HomeRepository {
            return AppEnvironment.current().repositoriesContext.homeRepository
        }
    }
}