package com.mizukikubota.tsundokubreak.ui.tsundoku

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.awesomedialog.*
import com.mizukikubota.tsundokubreak.R
import com.mizukikubota.tsundokubreak.bindLifecycleOwner
import com.mizukikubota.tsundokubreak.databinding.FragmentTsundokuBinding
import com.mizukikubota.tsundokubreak.databinding.ItemTsundokuRecyclerViewBinding
import com.mizukikubota.tsundokubreak.domain.entity.bookInfo.TsundokuBook
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber

@AndroidEntryPoint
class TsundokuFragment : Fragment() {

    val viewModel by viewModels<TsundokuViewModel>()

    private val tsundokuListAdapter by lazy { TsundokuListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentTsundokuBinding.inflate(inflater, container, false)
        .bindLifecycleOwner(viewLifecycleOwner) {
            lifecycleScope.launchWhenResumed {
                viewModel.tsundokuBookList.collect { resource ->
                    setEmptyStateVisible(it, resource)
                    tsundokuListAdapter.submitList(resource)
                }
            }

            it.recyclerView.apply {
                activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

                tsundokuListAdapter.setOnDokuryoButtonClickListener(
                    object : OnDokuryoButtonClickListener {

                        override fun onDokuryoButton(
                            lottieAnimationView: LottieAnimationView,
                            position: Int,
                            bookData: TsundokuBook
                        ) {
                            catflyButtonClick(lottieAnimationView, bookData)
                        }
                    }
                )
                adapter = tsundokuListAdapter
                layoutManager = LinearLayoutManager(context)
            }
            it.fab.setOnClickListener {
                findNavController().navigate(R.id.action_tsundoku_to_getBookInfo)
            }
        }

    private fun setEmptyStateVisible(it: FragmentTsundokuBinding, resource: List<TsundokuBook>) {
        if (resource.isEmpty()) {
            it.emptyImage.visibility = View.VISIBLE
            it.emptyText.visibility = View.VISIBLE
        } else {
            it.emptyImage.visibility = View.GONE
            it.emptyText.visibility = View.GONE
        }
    }

    class TsundokuViewHolder(val binding: ItemTsundokuRecyclerViewBinding) : RecyclerView.ViewHolder(
        binding.root
    )

    interface OnDokuryoButtonClickListener {
        fun onDokuryoButton(
            lottieAnimationView: LottieAnimationView,
            position: Int,
            bookData: TsundokuBook
        )
    }

    internal inner class TsundokuListAdapter :
        ListAdapter<TsundokuBook, TsundokuViewHolder>(TsundokuBookListDiff()) {
        private lateinit var listener: OnDokuryoButtonClickListener

        fun setOnDokuryoButtonClickListener(listener: OnDokuryoButtonClickListener) {
            this.listener = listener
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TsundokuViewHolder =
            TsundokuViewHolder(
                ItemTsundokuRecyclerViewBinding.inflate(
                    LayoutInflater.from(context),
                    parent,
                    false
                )
            )

        override fun onBindViewHolder(holder: TsundokuViewHolder, position: Int) {
            holder.binding.also {
                it.tsundokuBook = getItem(position)
                val bookData = currentList[position]
                val lottieAnimationView = it.dokuryoButton
                it.dokuryoButton.setOnClickListener {
                    listener.onDokuryoButton(lottieAnimationView, position, bookData)
                }

                it.tsundokuItem.setOnClickListener {
                    manageInputEditTextView(it)
                }

                it.readPages.doAfterTextChanged {
                    if (it.isNullOrEmpty()) {
                        viewModel.tsundokuSetReadPageCount(bookData, 0)
                    } else {
                        viewModel.tsundokuSetReadPageCount(bookData, it.toString().toInt())
                    }
                }
            }
        }
    }

    private inner class TsundokuBookListDiff : DiffUtil.ItemCallback<TsundokuBook>() {
        override fun areItemsTheSame(oldItem: TsundokuBook, newItem: TsundokuBook): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TsundokuBook, newItem: TsundokuBook): Boolean =
            oldItem == newItem
    }
}

private fun TsundokuFragment.showDokuryoDialog(bookData: TsundokuBook) {
    AwesomeDialog.build(requireActivity())
        .title("読了おめでとう！")
        .body("読了一覧へ追加移しますか？")
        .position(AwesomeDialog.POSITIONS.CENTER)
        .icon(R.raw.check_orange)
        .onPositive(
            "OK",
            R.color.orange
        ) {
            viewModel.tsundokuToDokuryo(bookData)
            Timber.d("dokuryoDialog:positive")
        }
        .onNegative(
            "cancel",
            R.color.gray
        ) {
            Timber.d("dokuryoDialog:negative")
        }
    activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}

private fun TsundokuFragment.catflyButtonClick(
    lottieAnimationView: LottieAnimationView,
    bookData: TsundokuBook
) {
    activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

    lottieAnimationView.playAnimation()

    Handler(Looper.getMainLooper()).postDelayed({
        showDokuryoDialog(bookData)
    }, 3000)
}

private fun TsundokuFragment.TsundokuListAdapter.manageInputEditTextView(it: View) {
    val inputMethodManager: InputMethodManager =
        it.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(
        it.windowToken,
        InputMethodManager.HIDE_NOT_ALWAYS
    )
    it.clearFocus()
}
