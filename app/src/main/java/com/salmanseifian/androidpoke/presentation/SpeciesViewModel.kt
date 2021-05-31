package com.salmanseifian.androidpoke.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.salmanseifian.androidpoke.data.repository.PokeRepository
import com.salmanseifian.androidpoke.presentation.mapper.PokemonSpeciesRepositoryToUiModelMapper
import com.salmanseifian.androidpoke.presentation.model.SpeciesUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpeciesViewModel @Inject constructor(
    private val pokeRepository: PokeRepository,
    private val pokemonSpeciesRepositoryToUiModelMapper: PokemonSpeciesRepositoryToUiModelMapper
) :
    ViewModel() {

    private val _allSpecies = MediatorLiveData<PagingData<SpeciesUiModel>>()
    val allSpecies: LiveData<PagingData<SpeciesUiModel>>
        get() = _allSpecies

    init {
        getAllPokemonSpecies()
    }

    private fun getAllPokemonSpecies() {
        viewModelScope.launch {
            pokeRepository.getAllPokemonSpecies()
                .cachedIn(viewModelScope)
                .collect {
                    val speciesUiModel = it.map {
                        pokemonSpeciesRepositoryToUiModelMapper.toUiModel(it)
                    }
                    _allSpecies.postValue(speciesUiModel)
                }
        }
    }


}