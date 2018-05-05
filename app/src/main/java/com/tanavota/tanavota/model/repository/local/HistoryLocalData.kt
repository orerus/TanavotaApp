package com.tanavota.tanavota.model.repository.local

import android.content.Context

/**
 */
object HistoryLocalData : SharedPreferenceBase<HistoryLocalDataKey>() {
    override fun createPreferenceInstance(context: Context) {
        sharedPreferences = context.getSharedPreferences("com.priceprice.pricepriceapp.history", Context.MODE_PRIVATE)
    }

}