package com.example.tsundokubreak.ui.dokuryo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tsundokubreak.bindLifecycleOwner
import com.example.tsundokubreak.databinding.FragmentDokuryoBinding
import com.example.tsundokubreak.databinding.ItemRecyclerViewBinding
import com.example.tsundokubreak.extensions.DataBindingAdapter

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
                adapter = PokemonItemListAdapter()
                layoutManager = LinearLayoutManager(context)
            }
        }

    private inner class PokemonItemListAdapter :
        DataBindingAdapter<ItemRecyclerViewBinding>(viewLifecycleOwner) {

        override fun onCreateViewDataBinding(
            layoutInflater: LayoutInflater,
            parent: ViewGroup,
            viewType: Int
        ): ItemRecyclerViewBinding = ItemRecyclerViewBinding.inflate(layoutInflater, parent, false)

        override fun onBindViewDataBinding(binding: ItemRecyclerViewBinding, position: Int) {
            binding.also {

                it.positionText = position.toString()
                it.pokemonText = pokemonList[position]
            }
        }

        override fun getItemCount(): Int = pokemonList.size ?: 0
    }
}
