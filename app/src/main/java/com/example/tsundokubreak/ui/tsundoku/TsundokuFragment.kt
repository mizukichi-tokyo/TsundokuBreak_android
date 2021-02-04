package com.example.tsundokubreak.ui.tsundoku

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
                val pokemonAdapter = PokemonItemListAdapter(context, pokemonList)

                pokemonAdapter.setOnBookCellClickListener(
                    object : PokemonItemListAdapter.OnBookCellClickListener {
                        override fun onItemClick(position: Int) {
                            findNavController().navigate(R.id.action_tsundoku_to_bookDetail)
                        }
                    }
                )

                adapter = pokemonAdapter
                layoutManager = LinearLayoutManager(context)
            }
            it.fab.setOnClickListener {
                findNavController().navigate(R.id.action_tsundoku_to_getBookInfo)
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
                it.imageButton.setOnClickListener {
                    listener.onItemClick(position)
                }
            }
        }

        override fun getItemCount(): Int = list.size
    }

    class MyViewHolder(val binding: ItemTsundokuRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root)
}
