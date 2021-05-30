package com.salmanseifian.androidpoke.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.salmanseifian.androidpoke.R
import com.salmanseifian.androidpoke.databinding.FragmentPokemonDetailsBinding
import com.salmanseifian.androidpoke.presentation.PokemonDetailsViewModel
import com.salmanseifian.androidpoke.utils.createImageUrl
import com.salmanseifian.androidpoke.utils.loadUrl
import dagger.hilt.android.AndroidEntryPoint
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

        lifecycleScope.launch {
            viewModel.getPokemonDetails(url)
        }

        viewModel.isLoading.observe(viewLifecycleOwner, {
            binding.progressCircular.isVisible = it == true
        })

        viewModel.pokemonDetails.observe(viewLifecycleOwner, {
            binding.apply {
                txtName.text = it.name
                txtDesc.text = it.flavorTextEntries
            }
        })

        viewModel.evolution.observe(viewLifecycleOwner, {
            binding.img.visibility = View.VISIBLE
            binding.txtEvolvesTo.visibility = View.VISIBLE
            binding.txtEvolvesToName.visibility = View.VISIBLE

            binding.img.loadUrl(it.url.createImageUrl())
            binding.txtEvolvesToName.text = it.name
        })
    }
}