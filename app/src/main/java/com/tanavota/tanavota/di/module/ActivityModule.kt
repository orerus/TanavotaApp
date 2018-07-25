package com.tanavota.tanavota.di.module

import com.github.kubode.rxeventbus.RxEventBus
import com.tanavota.tanavota.model.domain.common.RxEventBusStore
import dagger.Module
import dagger.Provides

@Module
class ActivityModule {
    @Provides
    fun provideRxEventBus(): RxEventBus = RxEventBusStore.bus
}