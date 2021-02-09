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
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.awesomedialog.*
import com.example.tsundokubreak.R
import com.example.tsundokubreak.bindLifecycleOwner
import com.example.tsundokubreak.databinding.FragmentTsundokuBinding
import com.example.tsundokubreak.databinding.ItemTsundokuRecyclerViewBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class TsundokuFragment : Fragment() {

    private val viewModel by viewModels<TsundokuViewModel>()

    private val BookList = listOf(
        "ピカチュウ",
        "カイリュー",
        "ヤドラン",
        "ピジョン",
        "コダック",
        "コラッタ",
        "ズバット",
        "ギャロップ",
        "サンダース",
        "メノクラゲ",
        "パウワウ",
        "カラカラ",
        "タマタマ",
        "ガラガラ",
        "フシギダネ"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentTsundokuBinding.inflate(inflater, container, false)
        .bindLifecycleOwner(viewLifecycleOwner) {
            it.recyclerView.apply {
                activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                val bookAdapter = BookItemListAdapter(context, BookList)

                bookAdapter.setOnDokuryoButtonClickListener(
                    object : BookItemListAdapter.OnDokuryoButtonClickListener {

                        override fun onDokuryoButton(
                            lottieAnimationView: LottieAnimationView,
                            position: Int
                        ) {
                            catflyButtonClick(lottieAnimationView)
                        }
                    }
                )
                adapter = bookAdapter
                layoutManager = LinearLayoutManager(context)
            }
            it.fab.setOnClickListener {
                findNavController().navigate(R.id.action_tsundoku_to_getBookInfo)
            }
        }

    class BookItemListAdapter(context: Context, val list: List<String>) : RecyclerView.Adapter<MyViewHolder>() {

        private lateinit var listener: OnDokuryoButtonClickListener

        interface OnDokuryoButtonClickListener {
            fun onDokuryoButton(lottieAnimationView: LottieAnimationView, position: Int)
        }

        fun setOnDokuryoButtonClickListener(listener: OnDokuryoButtonClickListener) {
            this.listener = listener
        }

        private val layoutInflater = LayoutInflater.from(context)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.item_tsundoku_recycler_view,
                parent,
                false
            )
        )

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.binding.let {
                it.positionText = position.toString()
                it.pokemonText = list[position]
                val lottieAnimationView = it.dokuryoButton
                it.dokuryoButton.setOnClickListener {
                    listener.onDokuryoButton(lottieAnimationView, position)
                }

                it.tsundokuItem.setOnClickListener {
                    manageInputEditTextView(it)
                }
            }
        }

        override fun getItemCount(): Int = list.size
    }

    class MyViewHolder(val binding: ItemTsundokuRecyclerViewBinding) : RecyclerView.ViewHolder(
        binding.root
    )
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

private fun TsundokuFragment.BookItemListAdapter.manageInputEditTextView(it: View) {
    val inputMethodManager: InputMethodManager =
        it.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(
        it.windowToken,
        InputMethodManager.HIDE_NOT_ALWAYS
    )
    it.clearFocus()
}
