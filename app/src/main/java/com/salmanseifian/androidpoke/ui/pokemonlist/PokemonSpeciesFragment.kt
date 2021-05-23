package com.salmanseifian.androidpoke.ui.pokemonlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.salmanseifian.androidpoke.R
import com.salmanseifian.androidpoke.databinding.FragmentPokemonSpeciesBinding
import com.salmanseifian.androidpoke.ui.pokemondetails.PokemonDetailsFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokemonSpeciesFragment : Fragment(R.layout.fragment_pokemon_species) {

    private val viewModel: PokemonSpeciesViewModel by viewModels()
    private val adapter = PokemonSpeciesAdapter { url: String? -> onItemClicked(url) }
    private var searchJob: Job? = null
    private lateinit var binding: FragmentPokemonSpeciesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentPokemonSpeciesBinding.bind(view)

        setUpAdapter()
        fetchPokemonSpecies()

        binding.swipeRefreshLayout.setOnRefreshListener {
            adapter.refresh()
        }
    }

    private fun setUpAdapter() {
        binding.allProductRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        binding.allProductRecyclerView.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter { adapter.retry() }
        )

        adapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {

                if (adapter.snapshot().isEmpty()) {
                    binding.progress.isVisible = true
                }
                binding.errorTxt.isVisible = false

            } else {
                binding.progress.isVisible = false
                binding.swipeRefreshLayout.isRefreshing = false

                val error = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error

                    else -> null
                }
                error?.let {
                    if (adapter.snapshot().isEmpty()) {
                        binding.errorTxt.isVisible = true
                        binding.errorTxt.text = it.error.localizedMessage
                    }

                }

            }
        }

    }

    private fun fetchPokemonSpecies(){
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchSpecies().collectLatest {
                adapter.submitData(it)
            }
        }
    }


    private fun onItemClicked(url: String?) {
        url?.let{
            findNavController().navigate(PokemonSpeciesFragmentDirections.toPokemonDetailsFFragment(url))
        }
    }

}