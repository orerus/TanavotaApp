package com.tanavota.tanavota.viewmodel.common

enum class DataLoadingState {
    Loading,
    Completed,
    Error;

    interface Delegate {
        fun onRetry()
    }
}