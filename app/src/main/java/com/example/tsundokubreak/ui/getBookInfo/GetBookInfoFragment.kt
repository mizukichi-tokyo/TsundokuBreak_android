package com.example.tsundokubreak.ui.getBookInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.tsundokubreak.bindLifecycleOwner
import com.example.tsundokubreak.databinding.FragmentGetBookInfoBinding
import com.example.tsundokubreak.domain.entity.common.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

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
                lifecycleScope.launchWhenResumed {

                    viewModel.gotBookInfo.collect() { resource ->
                        when (resource) {
                            is Resource.Empty -> {
                                it.tsundokuBook = viewModel.emptyBookInfo
                                viewModel.setRegisterBookState(false)
                            }
                            is Resource.InProgress -> {
                                it.tsundokuBook = viewModel.emptyBookInfo
                                viewModel.setRegisterBookState(false)
                            }
                            is Resource.Success -> {
                                it.tsundokuBook = resource.extractData
                                viewModel.setRegisterBookState(true)
                            }
                            is Resource.ApiError -> {
                                it.tsundokuBook = viewModel.emptyBookInfo
                                viewModel.setRegisterBookState(false)
                            }
                            is Resource.NetworkError -> {
                                it.tsundokuBook = viewModel.emptyBookInfo
                                viewModel.setRegisterBookState(false)
                            }
                        }
                    }
                }
                it.tsundokuBook = viewModel.emptyBookInfo
                it.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setIsbnString("9784873116594")
        viewModel.getBookInfoFromISBN()
    }
}
