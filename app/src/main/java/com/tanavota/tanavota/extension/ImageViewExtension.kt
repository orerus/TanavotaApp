package com.tanavota.tanavota.extension

import android.databinding.BindingAdapter
import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.tanavota.tanavota.R

@BindingAdapter("app:imageUrl")
fun loadImage(view: ImageView, imageUrl: String) {
    Picasso.with(view.context).load(imageUrl).error(R.drawable.no_image_dark).into(view)
}