package com.tanavota.tanavota.model.repository

import com.tanavota.tanavota.model.repository.home.HomeRepository

interface RepositoriesContext {
    val homeRepository: HomeRepository
}