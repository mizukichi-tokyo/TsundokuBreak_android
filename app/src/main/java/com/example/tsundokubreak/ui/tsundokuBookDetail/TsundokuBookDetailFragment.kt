package com.example.tsundokubreak.ui.tsundokuBookDetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tsundokubreak.R

class TsundokuBookDetailFragment : Fragment() {

    companion object {
        fun newInstance() = TsundokuBookDetailFragment()
    }

    private lateinit var viewModelTsundoku: TsundokuBookDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tsundoku_book_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelTsundoku = ViewModelProvider(this).get(TsundokuBookDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}