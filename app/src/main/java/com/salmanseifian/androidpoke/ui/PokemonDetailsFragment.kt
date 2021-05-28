package com.salmanseifian.androidpoke.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.salmanseifian.androidpoke.R
import com.salmanseifian.androidpoke.data.repository.Resource
import com.salmanseifian.androidpoke.databinding.FragmentPokemonDetailsBinding
import com.salmanseifian.androidpoke.presentation.PokemonDetailsViewModel
import com.salmanseifian.androidpoke.utils.createImageUrl
import com.salmanseifian.androidpoke.utils.loadUrl
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

        val args = PokemonDetailsFragmentArgs.fromBundle(requireArguments())
        val url = args.url

        loadPokemonDetails(url)
    }

    private fun loadPokemonDetails(url: String) {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.getPokemonDetails(url).collect {
                when (it) {
                    is Resource.Success -> {
                        binding.progressCircular.isVisible = false
                        binding.apply {
                            txtName.text = it.value.name
                            txtDesc.text = it.value.flavorTextEntries?.first {
                                it.language?.name.equals("en")
                            }?.flavorText
                        }

                        it.value.evolutionChain?.url?.let {
                            loadEvolutionChain(it)
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

    private fun loadEvolutionChain(url: String) {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.getEvolutionChain(url).collect {
                when (it) {
                    is Resource.Success -> {
                        binding.progressCircular.isVisible = false
                        it.value.evolvesTo.let { species ->
                            binding.img.loadUrl(species.url.createImageUrl())
                            binding.txtEvolvesToName.text = species.name
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