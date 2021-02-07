package com.example.tsundokubreak.ui.getBookInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tsundokubreak.bindLifecycleOwner
import com.example.tsundokubreak.databinding.FragmentGetBookInfoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GetBookInfoFragment : Fragment() {

    companion object {
        fun newInstance() = GetBookInfoFragment()
    }

    private val viewModel by viewModels<GetBookInfoViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentGetBookInfoBinding.inflate(inflater, container, false)
            .bindLifecycleOwner(viewLifecycleOwner) {
                it.tsundokuBook = viewModel.gotBookInfo.value
                it.canRegister = viewModel.canRegisterBook.value
    }
}
