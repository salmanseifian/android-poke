package com.salmanseifian.androidpoke.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.salmanseifian.androidpoke.R
import com.salmanseifian.androidpoke.databinding.FragmentPokemonSpeciesBinding
import com.salmanseifian.androidpoke.presentation.PokemonSpeciesViewModel
import com.salmanseifian.androidpoke.ui.adapter.LoadingStateAdapter
import com.salmanseifian.androidpoke.ui.adapter.PokemonSpeciesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class PokemonSpeciesFragment : Fragment(R.layout.fragment_pokemon_species) {

    private val viewModel: PokemonSpeciesViewModel by viewModels()
    private val adapter = PokemonSpeciesAdapter { url: String? -> onItemClicked(url) }
    private var searchJob: Job? = null
    private lateinit var binding: FragmentPokemonSpeciesBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fetchPokemonSpecies()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentPokemonSpeciesBinding.bind(view)

        setUpAdapter()

        binding.swipeRefreshLayout.setOnRefreshListener {
            adapter.refresh()
            adapter.submitData(lifecycle, PagingData.empty())
            fetchPokemonSpecies()
        }
    }

    private fun setUpAdapter() {
        binding.rvPokemonSpecies.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        binding.rvPokemonSpecies.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter { adapter.retry() }
        )

        adapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {

                if (adapter.snapshot().isEmpty()) {
                    binding.progress.isVisible = true
                }
                binding.txtErr.isVisible = false

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
                        binding.txtErr.isVisible = true
                        binding.txtErr.text = it.error.localizedMessage
                    }

                }

            }
        }

    }

    private fun fetchPokemonSpecies() {
        searchJob?.cancel()
        searchJob = lifecycleScope.launchWhenResumed {
            viewModel.getAllPokemonSpecies().collectLatest {
                adapter.submitData(it)
            }
        }
    }


    private fun onItemClicked(url: String?) {
        url?.let {
            findNavController().navigate(
                PokemonSpeciesFragmentDirections.toPokemonDetailsFFragment(
                    url
                )
            )
        }
    }

}