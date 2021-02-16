package com.mizukikubota.tsundokubreak.extensions

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.mizukikubota.tsundokubreak.R

@BindingAdapter("imageURL")
fun ImageView.imageURL(imageURL: String?) {
    imageURL ?: return
    Glide.with(context)
        .load(imageURL)
        .thumbnail(Glide.with(context).load(R.raw.loader))
        .into(this)
}
