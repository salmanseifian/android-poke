package com.salmanseifian.androidpoke.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.salmanseifian.androidpoke.databinding.ItemPokemonSpeciesBinding
import com.salmanseifian.androidpoke.presentation.model.SpeciesUiModel
import com.salmanseifian.androidpoke.utils.createImageUrl
import com.salmanseifian.androidpoke.utils.loadUrl


class SpeciesAdapter(private val clicked: (String?) -> Unit) :
    PagingDataAdapter<SpeciesUiModel, SpeciesAdapter.PlayersViewHolder>(
        SpeciesDiffCallback()
    ) {

    override fun onBindViewHolder(holder: PlayersViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayersViewHolder {
        return PlayersViewHolder(
            ItemPokemonSpeciesBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    }

    inner class PlayersViewHolder(
        private val binding: ItemPokemonSpeciesBinding
    ) : RecyclerView.ViewHolder(binding.root) {


        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val pokemon = getItem(position)
                    clicked.invoke(pokemon?.url)
                }
            }
        }

        fun bind(data: SpeciesUiModel?) {
            binding.let {
                it.txtName.text = data?.name
                it.img.loadUrl(data?.url?.createImageUrl())
            }

        }
    }

    private class SpeciesDiffCallback :
        DiffUtil.ItemCallback<SpeciesUiModel>() {
        override fun areItemsTheSame(
            oldItem: SpeciesUiModel,
            newItem: SpeciesUiModel
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: SpeciesUiModel,
            newItem: SpeciesUiModel
        ): Boolean {
            return oldItem == newItem
        }
    }

}