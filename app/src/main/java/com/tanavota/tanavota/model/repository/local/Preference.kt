package com.tanavota.tanavota.model.repository.local

import android.content.Context

object Preference : SharedPreferenceBase<PreferenceKey>() {

    override fun createPreferenceInstance(context: Context) {
        sharedPreferences = context.getSharedPreferences("com.tanavota.tanavota.preference", Context.MODE_PRIVATE)
    }

}