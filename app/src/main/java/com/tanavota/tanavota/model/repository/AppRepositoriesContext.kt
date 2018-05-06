package com.tanavota.tanavota.model.repository

import android.content.Context
import com.tanavota.tanavota.model.domain.home.HomeWebAPI

class AppRepositoriesContext(context: Context) : RepositoriesContext {
    override val homeRepository = HomeWebAPI(context)
}