package com.example.tsundokubreak.ui.dokuryo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.awesomedialog.*
import com.example.tsundokubreak.R
import com.example.tsundokubreak.bindLifecycleOwner
import com.example.tsundokubreak.databinding.FragmentDokuryoBinding
import com.example.tsundokubreak.databinding.ItemDokuryoRecyclerViewBinding
import com.example.tsundokubreak.domain.entity.bookInfo.TsundokuBook
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber

@AndroidEntryPoint
class DokuryoFragment : Fragment() {

    val viewModel by viewModels<DokuryoViewModel>()

    private val dokuryoListAdapter by lazy { DokuryoListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentDokuryoBinding.inflate(inflater, container, false)
            .bindLifecycleOwner(viewLifecycleOwner) {

                lifecycleScope.launchWhenResumed {
                    viewModel.dokuryoBookList.collect { resource ->
                        dokuryoListAdapter.submitList(resource)
                    }
                }

                it.recyclerView.apply {

                    dokuryoListAdapter.setOnDeleteButtonClickListener(
                        object : OnDeleteButtonClickListener {

                            override fun onDeleteButton(
                                lottieAnimationView: LottieAnimationView,
                                position: Int,
                                bookData: TsundokuBook
                            ) {
                                deleteButtonClick(lottieAnimationView, bookData)
                            }
                        }
                    )

                    adapter = dokuryoListAdapter
                    layoutManager = LinearLayoutManager(context)
                }
            }

    class DokuryoViewHolder(val binding: ItemDokuryoRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnDeleteButtonClickListener {
        fun onDeleteButton(
            lottieAnimationView: LottieAnimationView,
            position: Int,
            bookData: TsundokuBook
        )
    }

    internal inner class DokuryoListAdapter :
        ListAdapter<TsundokuBook, DokuryoViewHolder>(DokuryoBookListDiff()) {
        private lateinit var listener: OnDeleteButtonClickListener

        fun setOnDeleteButtonClickListener(listener: OnDeleteButtonClickListener) {
            this.listener = listener
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DokuryoViewHolder =
            DokuryoViewHolder(
                ItemDokuryoRecyclerViewBinding.inflate(
                    LayoutInflater.from(context),
                    parent,
                    false
                )
            )

        override fun onBindViewHolder(holder: DokuryoViewHolder, position: Int) {
            holder.binding.also {
                it.dokuryoBook = getItem(position)
                val bookData = currentList[position]
                val lottieAnimationView = it.deleteButton
                it.deleteButton.setOnClickListener {
                    listener.onDeleteButton(lottieAnimationView, position, bookData)
                }
            }
        }
    }

    private inner class DokuryoBookListDiff : DiffUtil.ItemCallback<TsundokuBook>() {
        override fun areItemsTheSame(oldItem: TsundokuBook, newItem: TsundokuBook): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TsundokuBook, newItem: TsundokuBook): Boolean =
            oldItem == newItem
    }
}

private fun DokuryoFragment.showDeleteDialog(bookData: TsundokuBook) {
    AwesomeDialog.build(requireActivity())
        .title("本当に削除しますか？")
        .body("元には戻せません")
        .position(AwesomeDialog.POSITIONS.CENTER)
        .icon(R.drawable.ic_baseline_delete_24)
        .onPositive(
            "OK",
            R.color.magenta
        ) {
            viewModel.deleteDokuryo(bookData)
            Timber.d("deleteDialog:OK")
        }
        .onNegative(
            "cancel",
            R.color.gray
        ) {
            Timber.d("deleteDialog:cancel")
        }
    activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}

private fun DokuryoFragment.deleteButtonClick(
    lottieAnimationView: LottieAnimationView,
    bookData: TsundokuBook
) {
    activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

    lottieAnimationView.playAnimation()

    Handler(Looper.getMainLooper()).postDelayed({
        showDeleteDialog(bookData)
    }, 2500)
}
