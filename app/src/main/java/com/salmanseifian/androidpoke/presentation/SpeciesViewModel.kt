package com.salmanseifian.androidpoke.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.salmanseifian.androidpoke.data.repository.PokeRepository
import com.salmanseifian.androidpoke.presentation.mapper.PokemonSpeciesRepositoryToUiModelMapper
import com.salmanseifian.androidpoke.presentation.model.SpeciesUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SpeciesViewModel @Inject constructor(
    private val pokeRepository: PokeRepository,
    private val pokemonSpeciesRepositoryToUiModelMapper: PokemonSpeciesRepositoryToUiModelMapper
) :
    ViewModel() {

    private var currentResult: Flow<PagingData<SpeciesUiModel>>? = null

    fun getAllPokemonSpecies(): Flow<PagingData<SpeciesUiModel>> {
        val newResult: Flow<PagingData<SpeciesUiModel>> =
            pokeRepository.getAllPokemonSpecies().cachedIn(viewModelScope).map { pagingData ->
                pagingData.map {
                    pokemonSpeciesRepositoryToUiModelMapper.toRepositoryModel(it)
                }
            }
        currentResult = newResult
        return newResult
    }


}