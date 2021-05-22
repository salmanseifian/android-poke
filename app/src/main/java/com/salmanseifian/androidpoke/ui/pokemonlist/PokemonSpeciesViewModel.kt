package com.salmanseifian.androidpoke.ui.pokemonlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.salmanseifian.androidpoke.data.model.PokemonSpecies
import com.salmanseifian.androidpoke.data.repository.RemotePokeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PokemonSpeciesViewModel @Inject constructor(private val repository: RemotePokeRepository) :
    ViewModel() {

    private var currentResult: Flow<PagingData<PokemonSpecies>>? = null

    fun searchSpecies(): Flow<PagingData<PokemonSpecies>> {
        val newResult: Flow<PagingData<PokemonSpecies>> =
            repository.getAllPokemonSpecies().cachedIn(viewModelScope)
        currentResult = newResult
        return newResult
    }


}