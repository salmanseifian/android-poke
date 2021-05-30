package com.salmanseifian.androidpoke.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.salmanseifian.androidpoke.data.repository.PokeRepository
import com.salmanseifian.androidpoke.data.repository.Resource
import com.salmanseifian.androidpoke.presentation.mapper.EvolutionChainRepositoryToUiModelMapper
import com.salmanseifian.androidpoke.presentation.mapper.PokemonDetailsRepositoryToUiModelMapper
import com.salmanseifian.androidpoke.presentation.model.PokemonDetailsUiModel
import com.salmanseifian.androidpoke.presentation.model.SpeciesUiModel
import com.salmanseifian.androidpoke.utils.extractSpeciesId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val pokeRepository: PokeRepository,
    private val pokemonDetailsRepositoryToUiModelMapper: PokemonDetailsRepositoryToUiModelMapper,
    private val evolutionChainRepositoryToUiModelMapper: EvolutionChainRepositoryToUiModelMapper
) :
    ViewModel() {

    private val _pokemonDetails = MutableLiveData<PokemonDetailsUiModel>()
    val pokemonDetails: LiveData<PokemonDetailsUiModel> = _pokemonDetails

    private val _evolution = MutableLiveData<SpeciesUiModel>()
    val evolution: LiveData<SpeciesUiModel> = _evolution

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    suspend fun getPokemonDetails(url: String) {
        val id = url.extractSpeciesId()
        pokeRepository.getPokemonDetails(id.toInt()).collect {
            when (it) {
                is Resource.Success -> {
                    _isLoading.value = false
                    _pokemonDetails.value =
                        pokemonDetailsRepositoryToUiModelMapper.toUiModel(it.value)
                    it.value.evolutionChainUrl?.let {
                        getEvolutionChain(url, it)
                    }
                }

                is Resource.Failure -> {
                    _isLoading.value = false
                }
            }
        }
    }

    suspend fun getEvolutionChain(url: String, chainUrl: String) {
        val chainId = chainUrl.extractSpeciesId()
        pokeRepository.getEvolutionChain(chainId.toInt()).collect {
            when (it) {
                is Resource.Success -> {
                    _isLoading.value = false
                    val uiModel = evolutionChainRepositoryToUiModelMapper.toUiModel(it.value)
                    uiModel.evolutions?.firstOrNull { it.first?.url == url }?.second?.let {
                        _evolution.value = it
                    }
                }

                is Resource.Failure -> {
                    _isLoading.value = false
                }
            }
        }
    }

}