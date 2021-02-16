package com.mizukikubota.tsundokubreak

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner

fun <T : ViewDataBinding> T.bindLifecycleOwner(
    lifecycleOwner: LifecycleOwner,
    bind: (T) -> Unit
): View = this.also {
    it.lifecycleOwner = lifecycleOwner
    bind(it)
}.root
