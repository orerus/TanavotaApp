package com.tanavota.tanavota.model.repository

import android.content.Context

class AppRepositoriesContext(context: Context) : RepositoriesContext {
    // TODO 実サーバーに置き換え
    override val homeRepository = MockRepositoriesContext.mockHomeRepository()
}