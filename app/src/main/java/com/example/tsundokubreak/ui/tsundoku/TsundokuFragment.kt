package com.example.tsundokubreak.ui.tsundoku

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
import com.example.tsundokubreak.domain.entity.common.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber

@AndroidEntryPoint
class TsundokuFragment : Fragment() {

    private val viewModel by viewModels<TsundokuViewModel>()

    private val tsundokuListAdapter by lazy { TsundokuListAdapter() }

    private var tsundokuBookList:List<TsundokuBook>
    = listOf(
        TsundokuBook(
            0,
            "リーダブルコード",
            "Dustin Boswell",
            237,
            "http://books.google.com/books/content?id=Wx1dLwEACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api")
        ,
        TsundokuBook(
            1,
            "リーダブルコード2",
            "Dustin Boswell2",
            2372,
            "http://books.google.com/books/content?id=Wx1dLwEACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api")
        ,
        TsundokuBook(
            2,
            "リーダブルコード3",
            "Dustin Boswell3",
            2373,
            "http://books.google.com/books/content?id=Wx1dLwEACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api")
        ,
    )


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
//                val bookAdapter = BookItemListAdapter(context, tsundokuBookList)

//                bookAdapter.setOnDokuryoButtonClickListener(
//                    object : BookItemListAdapter.OnDokuryoButtonClickListener {
//
//                        override fun onDokuryoButton(
//                            lottieAnimationView: LottieAnimationView,
//                            position: Int
//                        ) {
//                            catflyButtonClick(lottieAnimationView)
//                        }
//                    }
//                )
//                adapter = bookAdapter
                adapter = tsundokuListAdapter
                layoutManager = LinearLayoutManager(context)
            }
            it.fab.setOnClickListener {
                findNavController().navigate(R.id.action_tsundoku_to_getBookInfo)
            }
        }

//    class BookItemListAdapter(context: Context, val list: List<TsundokuBook>) : RecyclerView.Adapter<TsundokuViewHolder>() {
//
//        private lateinit var listener: OnDokuryoButtonClickListener
//
//        interface OnDokuryoButtonClickListener {
//            fun onDokuryoButton(lottieAnimationView: LottieAnimationView, position: Int)
//        }
//
//        fun setOnDokuryoButtonClickListener(listener: OnDokuryoButtonClickListener) {
//            this.listener = listener
//        }
//
//        private val layoutInflater = LayoutInflater.from(context)
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TsundokuViewHolder =
//        TsundokuViewHolder(
//            DataBindingUtil.inflate(
//                layoutInflater,
//                R.layout.item_tsundoku_recycler_view,
//                parent,
//                false
//            )
//        )
//
//        override fun onBindViewHolder(holder: TsundokuViewHolder, position: Int) {
//            holder.binding.let {
//                it.tsundokuBook = list[position]
//                val lottieAnimationView = it.dokuryoButton
//                it.dokuryoButton.setOnClickListener {
//                    listener.onDokuryoButton(lottieAnimationView, position)
//                }
//
//                it.tsundokuItem.setOnClickListener {
//                    manageInputEditTextView(it)
//                }
//            }
//        }
//
//        override fun getItemCount(): Int = list.size
//    }

    class TsundokuViewHolder(val binding: ItemTsundokuRecyclerViewBinding) : RecyclerView.ViewHolder(
        binding.root
    )

    private inner class TsundokuListAdapter :
        ListAdapter<TsundokuBook, TsundokuViewHolder>(TsundokuBookListDiff()) {

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
//                    listener.onDokuryoButton(lottieAnimationView, position)
                }

                it.tsundokuItem.setOnClickListener {
//                    manageInputEditTextView(it)
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

//private fun TsundokuFragment.BookItemListAdapter.manageInputEditTextView(it: View) {
//    val inputMethodManager: InputMethodManager =
//        it.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//    inputMethodManager.hideSoftInputFromWindow(
//        it.windowToken,
//        InputMethodManager.HIDE_NOT_ALWAYS
//    )
//    it.clearFocus()
//}
