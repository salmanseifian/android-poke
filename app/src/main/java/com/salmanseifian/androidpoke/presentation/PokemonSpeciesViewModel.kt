package com.salmanseifian.androidpoke.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.salmanseifian.androidpoke.data.model.SpeciesRepositoryModel
import com.salmanseifian.androidpoke.data.repository.PokeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PokemonSpeciesViewModel @Inject constructor(private val pokeRepository: PokeRepository) :
    ViewModel() {

    private var currentResult: Flow<PagingData<SpeciesRepositoryModel>>? = null

    fun getAllPokemonSpecies(): Flow<PagingData<SpeciesRepositoryModel>> {
        val newResult: Flow<PagingData<SpeciesRepositoryModel>> =
            pokeRepository.getAllPokemonSpecies().cachedIn(viewModelScope)
        currentResult = newResult
        return newResult
    }


}