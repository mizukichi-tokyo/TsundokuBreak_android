package com.example.tsundokubreak.ui.dokuryo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tsundokubreak.R

class DokuryoFragment : Fragment() {

    private lateinit var dokuryoViewModel: DokuryoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dokuryoViewModel =
                ViewModelProvider(this).get(DokuryoViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dokuryo, container, false)
        val textView: TextView = root.findViewById(R.id.text_Dokuryo)
        dokuryoViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
