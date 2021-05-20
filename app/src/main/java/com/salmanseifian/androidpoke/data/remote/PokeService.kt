package com.salmanseifian.androidpoke.data.remote

import com.salmanseifian.androidpoke.model.PokemonSpeciesResponse
import retrofit2.Response
import retrofit2.http.GET

interface PokeService {

    @GET("/v2/pokemon-species")
    suspend fun getPokemonSpecies(): Response<PokemonSpeciesResponse>


    companion object{
        const val POKE_API_URL = "https://pokeapi.co/api/"
    }

}