package com.example.tsundokubreak.ui.tsundoku

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.awesomedialog.*
import com.example.tsundokubreak.R
import com.example.tsundokubreak.bindLifecycleOwner
import com.example.tsundokubreak.databinding.FragmentTsundokuBinding
import com.example.tsundokubreak.databinding.ItemTsundokuRecyclerViewBinding

class TsundokuFragment : Fragment() {

    private lateinit var tsundokuViewModel: TsundokuViewModel

    val pokemonList = listOf(
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
                val pokemonAdapter = PokemonItemListAdapter(context, pokemonList)

                pokemonAdapter.setOnBookCellClickListener(
                    object : PokemonItemListAdapter.OnBookCellClickListener {
                        override fun onItemClick(position: Int) {
                            transitionDelay()
                        }
                    }
                )

                adapter = pokemonAdapter
                layoutManager = LinearLayoutManager(context)
            }
            it.fab.setOnClickListener {
                findNavController().navigate(R.id.action_tsundoku_to_getBookInfo)
            }

            showDokuryoDialog()

        }

    private fun transitionDelay() {
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.action_tsundoku_to_bookDetail)
        }, 1000)
    }

    private fun showDokuryoDialog(){
        AwesomeDialog.build(activity!!)
            .title("読了おめでとう！")
            .body("読了一覧へ追加移しますか？")
            .position(AwesomeDialog.POSITIONS.CENTER)
            .icon(R.raw.check_orange)
            .onPositive(
                "OK",
                R.color.orange
            ) {
                Log.d("TAG", "positive ")
            }
            .onNegative(
                "cancel",
                R.color.gray
            ) {
                Log.d("TAG", "negative ")
            }

    }

    class PokemonItemListAdapter(context: Context, val list: List<String>) : RecyclerView.Adapter<MyViewHolder>() {

        private lateinit var listener: OnBookCellClickListener

        interface OnBookCellClickListener {
            fun onItemClick(position: Int)
        }

        fun setOnBookCellClickListener(listener: OnBookCellClickListener) {
            this.listener = listener
        }

        private val layoutInflater = LayoutInflater.from(context)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(
                DataBindingUtil.inflate(layoutInflater, R.layout.item_tsundoku_recycler_view, parent, false)
        )

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.binding.let {
                it.positionText = position.toString()
                it.pokemonText = list[position]
                it.chartButton.setOnClickListener {
                    listener.onItemClick(position)
                }
            }
        }

        override fun getItemCount(): Int = list.size
    }

    class MyViewHolder(val binding: ItemTsundokuRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root)
}
