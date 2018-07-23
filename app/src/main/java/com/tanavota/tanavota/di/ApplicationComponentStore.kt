package com.tanavota.tanavota.di

import com.tanavota.tanavota.di.component.ApplicationComponent

object ApplicationComponentStore {
    lateinit var applicationComponent: ApplicationComponent

    fun initialize(component: ApplicationComponent) {
        this.applicationComponent = component
    }

    fun get(): ApplicationComponent = this.applicationComponent
}