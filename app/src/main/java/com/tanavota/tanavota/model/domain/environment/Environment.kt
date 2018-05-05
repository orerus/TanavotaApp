package com.tanavota.tanavota.model.domain.environment

import android.content.Context
import com.tanavota.tanavota.model.domain.Server
import com.tanavota.tanavota.model.repository.AppRepositoriesContext
import com.tanavota.tanavota.model.repository.RepositoriesContext

/**
 * アプリケーションが動くために必要な環境を定義します。
 */
class Environment {
    val repositoriesContext: RepositoriesContext
    val server: Server

    /**
     * 通常使われるべき環境を作成するコンストラクタ
     */
    constructor(context: Context) {
        repositoriesContext = AppRepositoriesContext(context)
        server = Server.current
    }

    /**
     * 特定のものだけを入れ替えるコンストラクタ
     */
    constructor(
            repositoriesContext: RepositoriesContext = AppEnvironment.current().repositoriesContext,
            server: Server = AppEnvironment.current().server
    ) {
        this.repositoriesContext = repositoriesContext
        this.server = server
    }
}