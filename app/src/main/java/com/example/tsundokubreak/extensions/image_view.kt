package com.example.tsundokubreak.extensions

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageURL")
fun ImageView.imageURL(imageURL: String) {
    imageURL ?: return
    Glide.with(context)
        .load(imageURL)
        .into(this)
}