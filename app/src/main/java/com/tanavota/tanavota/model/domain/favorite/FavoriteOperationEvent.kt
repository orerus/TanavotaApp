package com.tanavota.tanavota.model.domain.favorite

class FavoriteOperationEvent(
        val articleIds: List<String>,
        val mode: Mode
) {
    enum class Mode {
        Add,
        Remove
    }
}