package com.example.tsundokubreak.ui.getBookInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tsundokubreak.bindLifecycleOwner
import com.example.tsundokubreak.databinding.FragmentGetBookInfoBinding

class GetBookInfoFragment : Fragment() {

    companion object {
        fun newInstance() = GetBookInfoFragment()
    }

    private lateinit var viewModel: GetBookInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentGetBookInfoBinding.inflate(inflater, container, false)
            .bindLifecycleOwner(viewLifecycleOwner) {
    }
}
