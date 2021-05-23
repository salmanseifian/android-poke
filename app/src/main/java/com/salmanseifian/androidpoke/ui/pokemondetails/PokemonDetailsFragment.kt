package com.salmanseifian.androidpoke.ui.pokemondetails

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.salmanseifian.androidpoke.R
import com.salmanseifian.androidpoke.data.Resource
import com.salmanseifian.androidpoke.databinding.FragmentPokemonDetailsBinding
import com.salmanseifian.androidpoke.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokemonDetailsFragment : Fragment(R.layout.fragment_pokemon_details) {

    private val viewModel: PokemonDetailsViewModel by viewModels()
    private lateinit var binding: FragmentPokemonDetailsBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentPokemonDetailsBinding.bind(view)

        loadPokemonDetails()

    }

    private fun loadPokemonDetails() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.getPokemonDetails("").collect {
                when (it) {
                    is Resource.Success -> {
                        binding.progressCircular.isVisible = false
                        binding.apply {
                            txtName.text = it.value.name
                            txtDesc.text = it.value.flavorTextEntries?.first {
                                it.language?.equals("en") ?: false
                            }?.flavorText
                        }
                    }

                    is Resource.Failure -> {
                        binding.progressCircular.isVisible = false
                        requireContext().toast(R.string.err_loading_pokemon_details)
                    }

                    is Resource.Loading -> {
                        binding.progressCircular.isVisible = true
                    }
                }
            }
        }
    }


}