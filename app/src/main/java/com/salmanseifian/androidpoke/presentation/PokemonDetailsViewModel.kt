package com.salmanseifian.androidpoke.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salmanseifian.androidpoke.data.repository.PokeRepository
import com.salmanseifian.androidpoke.presentation.mapper.EvolutionChainRepositoryToUiModelMapper
import com.salmanseifian.androidpoke.presentation.mapper.PokemonDetailsRepositoryToUiModelMapper
import com.salmanseifian.androidpoke.presentation.model.PokemonDetailsUiModel
import com.salmanseifian.androidpoke.presentation.model.SpeciesUiModel
import com.salmanseifian.androidpoke.utils.extractSpeciesId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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

    fun getPokemonDetails(url: String) {
        viewModelScope.launch {
            val id = url.extractSpeciesId()
            pokeRepository.getPokemonDetails(id.toInt())
                .collect { result ->
                    when {
                        result.isSuccess -> {
                            _isLoading.value = false

                            result.getOrNull()?.let {
                                _pokemonDetails.value =
                                    pokemonDetailsRepositoryToUiModelMapper.toUiModel(it)
                                getEvolutionChain(url, it.evolutionChainUrl)
                            }
                        }

                        result.isFailure -> {
                            _isLoading.value = false
                        }
                    }
                }
        }
    }

    fun getEvolutionChain(url: String, chainUrl: String?) {
        chainUrl?.let {
            viewModelScope.launch {
                val chainId = it.extractSpeciesId()
                pokeRepository.getEvolutionChain(chainId.toInt()).collect { result ->
                    when {
                        result.isSuccess -> {
                            _isLoading.value = false
                            result.getOrNull()?.let {
                                val uiModel =
                                    evolutionChainRepositoryToUiModelMapper.toUiModel(it)
                                uiModel.evolutions?.firstOrNull { it.first?.url == url }?.second?.let {
                                    _evolution.value = it
                                }
                            }

                        }

                        result.isFailure -> {
                            _isLoading.value = false
                        }
                    }
                }
            }
        }

    }

}