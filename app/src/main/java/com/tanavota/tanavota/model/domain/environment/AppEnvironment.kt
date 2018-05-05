package com.tanavota.tanavota.model.domain.environment

import android.content.Context
import com.tanavota.tanavota.BuildConfig
import com.tanavota.tanavota.model.repository.MockRepositoriesContext
import java.util.*
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

object AppEnvironment {
    private val lock = ReentrantLock()
    private val stack = Stack<Environment>()

    fun initialize(context: Context) {
        lock.withLock {
            stack.removeAllElements()
            stack.push(Environment(context))

            if (BuildConfig.BUILD_TYPE == "debug") {
                stack.push(Environment(
                        repositoriesContext = MockRepositoriesContext(
//                                homeRepo = HomeWebAPI()
                        )
                ))
            }
        }
    }

    fun current(): Environment {
        lock.withLock {
            return stack.peek()
        }
    }

    fun pushEnvironment(environment: Environment) {
        stack.push(environment)
    }
}