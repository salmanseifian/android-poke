package com.salmanseifian.androidpoke.ui.pokemonlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.salmanseifian.androidpoke.data.model.PokemonSpecies
import com.salmanseifian.androidpoke.databinding.ItemPokemonSpeciesBinding
import com.salmanseifian.androidpoke.utils.createImageUrl
import com.salmanseifian.androidpoke.utils.loadUrl


class PokemonSpeciesAdapter(private val clicked: (String?) -> Unit) :
    PagingDataAdapter<PokemonSpecies, PokemonSpeciesAdapter.PlayersViewHolder>(
        PokemonSpeciesDiffCallback()
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

        fun bind(data: PokemonSpecies?) {
            binding.let {
                it.root.setOnClickListener {
                    clicked.invoke(data?.url)
                }
                it.txtName.text = data?.name
                it.img.loadUrl(data?.url?.createImageUrl())
            }

        }
    }

    private class PokemonSpeciesDiffCallback : DiffUtil.ItemCallback<PokemonSpecies>() {
        override fun areItemsTheSame(oldItem: PokemonSpecies, newItem: PokemonSpecies): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PokemonSpecies, newItem: PokemonSpecies): Boolean {
            return oldItem == newItem
        }
    }

}