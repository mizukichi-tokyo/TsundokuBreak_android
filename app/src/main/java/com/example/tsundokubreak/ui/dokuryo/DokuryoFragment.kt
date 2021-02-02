package com.example.tsundokubreak.ui.dokuryo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tsundokubreak.R
import com.example.tsundokubreak.bindLifecycleOwner
import com.example.tsundokubreak.databinding.FragmentDokuryoBinding
import com.example.tsundokubreak.databinding.ItemRecyclerViewBinding

class DokuryoFragment : Fragment() {

    private lateinit var dokuryoViewModel: DokuryoViewModel

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
    ): View = FragmentDokuryoBinding.inflate(inflater, container, false)
            .bindLifecycleOwner(viewLifecycleOwner) {
                it.recyclerView.apply {
                    adapter = PokemonItemListAdapter(context, pokemonList)
                    layoutManager = LinearLayoutManager(context)
                }
            }

    class PokemonItemListAdapter(context: Context, val list: List<String>) : RecyclerView.Adapter<MyViewHolder>() {
        private val layoutInflater = LayoutInflater.from(context)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(
                DataBindingUtil.inflate(layoutInflater, R.layout.item_recycler_view, parent, false)
        )

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.binding.let {
                it.positionText = position.toString()
                it.pokemonText = list[position]
            }
        }

        override fun getItemCount(): Int = list.size
    }

    class MyViewHolder(val binding: ItemRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root)
}
