package com.example.tsundokubreak.ui.getBookInfo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
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

    private val viewModel by viewModels<GetBookInfoViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentGetBookInfoBinding.inflate(inflater, container, false)
        .bindLifecycleOwner(viewLifecycleOwner) {
            lifecycleScope.launchWhenResumed {

                viewModel.gotBookInfo.collect { resource ->
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

            it.isbnEditText.setOnEditorActionListener { view, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    viewModel.getBookInfoFromISBN()
                    val inputManager =
                        activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputManager.hideSoftInputFromWindow(
                        view.windowToken,
                        InputMethodManager.HIDE_NOT_ALWAYS
                    )
                }
                return@setOnEditorActionListener true
            }

            it.registerButton.setOnClickListener {
                activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                viewModel.clickRegister({
                    Toast.makeText(context, "積読に追加しました", Toast.LENGTH_SHORT).show()
                }
                ) {
                    Toast.makeText(context, "積読に追加に失敗しました", Toast.LENGTH_SHORT).show()
                    activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                }
                parentFragmentManager.popBackStack()
            }
        }
}
