package com.tanavota.tanavota.viewmodel.common

import android.databinding.ObservableBoolean

class FavoriteButtonModel(val articleId: String, isFavorite: Boolean) {
    val isFavorite = ObservableBoolean(isFavorite)
}