package com.tanavota.tanavota.viewmodel.home

import android.app.Application
import com.nhaarman.mockitokotlin2.*
import com.tanavota.tanavota.BuildConfig
import com.tanavota.tanavota.di.ApplicationComponentStore
import com.tanavota.tanavota.di.module.ApplicationModule
import com.tanavota.tanavota.model.domain.home.HomeModel
import io.reactivex.disposables.CompositeDisposable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
class HomeViewModelTest {
    private lateinit var application: Application

    @Before
    fun setup() {
        application = RuntimeEnvironment.application
    }

    @Test
    fun testLoadNext() {
        val mockHomeModel = mock<HomeModel> {
            on { hasNext } doReturn true
            on { subscribe(any()) } doReturn CompositeDisposable()
        }
        ApplicationComponentStore.initialize(
                DaggerTestApplicationComponent
                        .builder()
                        .applicationModule(ApplicationModule(application = application))
                        .testModelModule(TestModelModule(mockHomeModel = mockHomeModel))
                        .build()
        )

        val mockDelegate = mock<HomeViewModel.Delegate>()
        val homeViewModel = HomeViewModel(mockDelegate)

        homeViewModel.loadNext()
        verify(mockHomeModel, times(1)).subscribe(homeViewModel)
        verify(mockHomeModel, times(1)).loadNext()
    }
}