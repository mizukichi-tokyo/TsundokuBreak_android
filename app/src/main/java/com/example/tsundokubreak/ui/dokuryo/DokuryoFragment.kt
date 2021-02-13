package com.example.tsundokubreak.ui.dokuryo

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.awesomedialog.*
import com.example.tsundokubreak.R
import com.example.tsundokubreak.bindLifecycleOwner
import com.example.tsundokubreak.databinding.FragmentDokuryoBinding
import com.example.tsundokubreak.databinding.ItemDokuryoRecyclerViewBinding
import com.example.tsundokubreak.domain.entity.bookInfo.TsundokuBook
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class DokuryoFragment : Fragment() {

    private val viewModel by viewModels<DokuryoViewModel>()

    private var dokuryoBookList:List<TsundokuBook>
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
    ): View = FragmentDokuryoBinding.inflate(inflater, container, false)
            .bindLifecycleOwner(viewLifecycleOwner) {
                it.recyclerView.apply {
                    val dokuryoBookAdapter = DokuryoBookItemListAdapter(context, dokuryoBookList)

                    dokuryoBookAdapter.setOnDeleteButtonClickListener(
                        object : DokuryoBookItemListAdapter.OnDeleteButtonClickListener {

                            override fun onDeleteButton(
                                lottieAnimationView: LottieAnimationView,
                                position: Int
                            ) {
                                deleteButtonClick(lottieAnimationView)
                            }
                        }
                    )
                    adapter = dokuryoBookAdapter
                    layoutManager = LinearLayoutManager(context)
                }
            }

    class DokuryoBookItemListAdapter(context: Context, val list: List<TsundokuBook>) : RecyclerView.Adapter<DokuryoItemViewHolder>() {

        private lateinit var listener: OnDeleteButtonClickListener

        interface OnDeleteButtonClickListener {
            fun onDeleteButton(lottieAnimationView: LottieAnimationView, position: Int)
        }

        fun setOnDeleteButtonClickListener(listener: OnDeleteButtonClickListener) {
            this.listener = listener
        }

        private val layoutInflater = LayoutInflater.from(context)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DokuryoItemViewHolder =
            DokuryoItemViewHolder(
                DataBindingUtil.inflate(
                    layoutInflater,
                    R.layout.item_dokuryo_recycler_view,
                    parent,
                    false
                )
        )

        override fun onBindViewHolder(holder: DokuryoItemViewHolder, position: Int) {
            holder.binding.let {
                it.dokuryoBook = list[position]

                val lottieDeleteView = it.deleteButton
                it.deleteButton.setOnClickListener {
                    listener.onDeleteButton(lottieDeleteView, position)
                }
            }
        }

        override fun getItemCount(): Int = list.size
    }

    class DokuryoItemViewHolder(val binding: ItemDokuryoRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root)
}

private fun DokuryoFragment.showDeleteDialog() {
    AwesomeDialog.build(requireActivity())
        .title("本当に削除しますか？")
        .body("元には戻せません")
        .position(AwesomeDialog.POSITIONS.CENTER)
        .icon(R.drawable.ic_baseline_delete_24)
        .onPositive(
            "OK",
            R.color.magenta
        ) {
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

private fun DokuryoFragment.deleteButtonClick(lottieAnimationView: LottieAnimationView) {
    activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

    lottieAnimationView.playAnimation()

    Handler(Looper.getMainLooper()).postDelayed({
        showDeleteDialog()
    }, 2500)
}
