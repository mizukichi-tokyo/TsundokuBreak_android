package com.example.tsundokubreak.ui.tsundoku

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
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
import com.example.tsundokubreak.R
import com.example.tsundokubreak.bindLifecycleOwner
import com.example.tsundokubreak.databinding.FragmentTsundokuBinding
import com.example.tsundokubreak.databinding.ItemTsundokuRecyclerViewBinding
import com.example.tsundokubreak.domain.entity.bookInfo.TsundokuBook
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber

@AndroidEntryPoint
class TsundokuFragment : Fragment() {

    private val viewModel by viewModels<TsundokuViewModel>()

    private val tsundokuListAdapter by lazy { TsundokuListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentTsundokuBinding.inflate(inflater, container, false)
        .bindLifecycleOwner(viewLifecycleOwner) {
            lifecycleScope.launchWhenResumed {
                viewModel.tsundokuBookList.collect { resource ->
                    tsundokuListAdapter.submitList(resource)
                }
            }

            it.recyclerView.apply {
                activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

                tsundokuListAdapter.setOnDokuryoButtonClickListener(
                    object : OnDokuryoButtonClickListener {

                        override fun onDokuryoButton(
                            lottieAnimationView: LottieAnimationView,
                            position: Int
                        ) {
                            catflyButtonClick(lottieAnimationView)
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

    class TsundokuViewHolder(val binding: ItemTsundokuRecyclerViewBinding) : RecyclerView.ViewHolder(
        binding.root
    )

    interface OnDokuryoButtonClickListener {
        fun onDokuryoButton(lottieAnimationView: LottieAnimationView, position: Int)
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
                val lottieAnimationView = it.dokuryoButton
                it.dokuryoButton.setOnClickListener {
                    listener.onDokuryoButton(lottieAnimationView, position)
                }

                it.tsundokuItem.setOnClickListener {
                    manageInputEditTextView(it)
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

private fun TsundokuFragment.showDokuryoDialog() {
    AwesomeDialog.build(requireActivity())
        .title("読了おめでとう！")
        .body("読了一覧へ追加移しますか？")
        .position(AwesomeDialog.POSITIONS.CENTER)
        .icon(R.raw.check_orange)
        .onPositive(
            "OK",
            R.color.orange
        ) {
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

private fun TsundokuFragment.catflyButtonClick(lottieAnimationView: LottieAnimationView) {
    activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

    lottieAnimationView.playAnimation()

    Handler(Looper.getMainLooper()).postDelayed({
        showDokuryoDialog()
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
