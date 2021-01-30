package com.example.tsundokubreak.extensions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView

class BindingViewHolder<T : ViewDataBinding>(val binding: T) : RecyclerView.ViewHolder(binding.root)

abstract class DataBindingAdapter<T : ViewDataBinding>(val lifecycleOwner: LifecycleOwner?) :
        RecyclerView.Adapter<BindingViewHolder<T>>() {
    abstract fun onCreateViewDataBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): T

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingViewHolder<T> = LayoutInflater.from(parent.context)
            .run { onCreateViewDataBinding(this, parent, viewType) }
            .run { BindingViewHolder(this) }

    abstract fun onBindViewDataBinding(binding: T, position: Int)

    override fun onBindViewHolder(holder: BindingViewHolder<T>, position: Int) {
        holder.binding.also {
            it.lifecycleOwner = lifecycleOwner
            onBindViewDataBinding(it, position)
            it.executePendingBindings()
        }
    }
}
