package com.tanavota.tanavota.model.domain.environment

import android.content.Context
import com.tanavota.tanavota.model.domain.KeyValueStore
import com.tanavota.tanavota.model.domain.Server
import com.tanavota.tanavota.model.repository.AppRepositoriesContext
import com.tanavota.tanavota.model.repository.RepositoriesContext
import com.tanavota.tanavota.model.repository.local.Preference
import com.tanavota.tanavota.model.repository.local.PreferenceKey

/**
 * アプリケーションが動くために必要な環境を定義します。
 */
class Environment {
    val preference: KeyValueStore<PreferenceKey>
    val repositoriesContext: RepositoriesContext
    val server: Server

    /**
     * 通常使われるべき環境を作成するコンストラクタ
     */
    constructor(context: Context) {
        preference = Preference
        repositoriesContext = AppRepositoriesContext(context)
        server = Server.current
    }

    /**
     * 特定のものだけを入れ替えるコンストラクタ
     */
    constructor(
            preference: KeyValueStore<PreferenceKey> = AppEnvironment.current().preference,
            repositoriesContext: RepositoriesContext = AppEnvironment.current().repositoriesContext,
            server: Server = AppEnvironment.current().server
    ) {
        this.preference = preference
        this.repositoriesContext = repositoriesContext
        this.server = server
    }
}