package com.example.tsundokubreak.ui.tsundoku

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tsundokubreak.R

class TsundokuFragment : Fragment() {

    private lateinit var tsundokuViewModel: TsundokuViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tsundokuViewModel =
                ViewModelProvider(this).get(TsundokuViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_tsundoku, container, false)
        val textView: TextView = root.findViewById(R.id.text_tsundoku)
        tsundokuViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
