package com.example.tsundokubreak.ui.tsundoku

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tsundokubreak.R
import com.example.tsundokubreak.databinding.FragmentTsundokuBinding

class TsundokuFragment : Fragment() {

    private lateinit var tsundokuViewModel: TsundokuViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentTsundokuBinding.inflate(inflater, container, false).bindLifecycleOwner(viewLifecycleOwner) {

            }
}

fun <T : ViewDataBinding>T.bindLifecycleOwner(
        lifecycleOwner: LifecycleOwner,
        bind: (T) -> Unit
): View = this.also {
    it.lifecycleOwner = lifecycleOwner
    bind(it)
}.root
