package com.example.tsundokubreak.ui.dokuryo

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.awesomedialog.*
import com.example.tsundokubreak.R
import com.example.tsundokubreak.bindLifecycleOwner
import com.example.tsundokubreak.databinding.FragmentDokuryoBinding
import com.example.tsundokubreak.databinding.ItemDokuryoRecyclerViewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DokuryoFragment : Fragment() {

//    private lateinit var dokuryoViewModel: DokuryoViewModel
    private val viewModel by viewModels<DokuryoViewModel>()

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
                AwesomeDialog.build(requireActivity())
                    .title("本当に削除しますか？")
                    .body("元には戻せません")
                    .position(AwesomeDialog.POSITIONS.CENTER)
                    .icon(R.drawable.ic_baseline_delete_24)
                    .onPositive(
                        "OK",
                            R.color.magenta
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
        private val layoutInflater = LayoutInflater.from(context)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(
                DataBindingUtil.inflate(layoutInflater, R.layout.item_dokuryo_recycler_view, parent, false)
        )

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.binding.let {
                it.positionText = position.toString()
                it.pokemonText = list[position]
            }
        }

        override fun getItemCount(): Int = list.size
    }

    class MyViewHolder(val binding: ItemDokuryoRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root)
}
