package com.salmanseifian.androidpoke.ui.pokemondetails

import androidx.lifecycle.ViewModel
import com.salmanseifian.androidpoke.data.repository.Resource
import com.salmanseifian.androidpoke.data.repository.RemotePokeRepository
import com.salmanseifian.androidpoke.utils.extractSpeciesId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(private val repository: RemotePokeRepository) :
    ViewModel() {


    suspend fun getPokemonDetails(url: String) = flow {
        val id = url.extractSpeciesId()
        emit(Resource.Loading)
        emit(repository.getPokemonDetails(id.toInt()))
    }

    suspend fun getEvolutionChain(chainUrl: String) = flow {
        val chainId = chainUrl.extractSpeciesId()
        emit(Resource.Loading)
        emit(repository.getEvolutionChain(chainId.toInt()))
    }


}