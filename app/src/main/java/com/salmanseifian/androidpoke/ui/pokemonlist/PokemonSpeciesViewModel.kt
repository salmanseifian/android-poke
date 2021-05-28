package com.salmanseifian.androidpoke.ui.pokemonlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.salmanseifian.androidpoke.data.model.PokemonRepositoryModel
import com.salmanseifian.androidpoke.data.model.PokemonSpeciesRepositoryModel
import com.salmanseifian.androidpoke.data.repository.PokeRepository
import com.salmanseifian.androidpoke.data_api.model.PokemonSpecies
import com.salmanseifian.androidpoke.data.repository.PokeRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PokemonSpeciesViewModel @Inject constructor(private val pokeRepository: PokeRepository) :
    ViewModel() {

    private var currentResult: Flow<PagingData<PokemonRepositoryModel>>? = null

    fun searchSpecies(): Flow<PagingData<PokemonRepositoryModel>> {
        val newResult: Flow<PagingData<PokemonRepositoryModel>> =
            pokeRepository.getAllPokemonSpecies().cachedIn(viewModelScope)
        currentResult = newResult
        return newResult
    }


}