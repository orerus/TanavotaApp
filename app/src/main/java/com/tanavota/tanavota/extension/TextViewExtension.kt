package com.tanavota.tanavota.extension

import android.databinding.BindingAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.tanavota.tanavota.R

@BindingAdapter("app:resourceId")
fun textFromResourceId(view: TextView, resourceId: Int) {
    view.text = view.context.getString(resourceId)
}