package com.salmanseifian.androidpoke.data.remote

import com.salmanseifian.androidpoke.data.model.EvolutionChainResponse
import com.salmanseifian.androidpoke.data.model.PokemonDetailsResponse
import com.salmanseifian.androidpoke.data.model.PokemonSpeciesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeService {

    @GET("v2/pokemon-species")
    suspend fun getPokemonSpecies(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): PokemonSpeciesResponse

    @GET("v2/pokemon-species/{id}/")
    suspend fun getPokemonDetails(@Path("id") id: Int): PokemonDetailsResponse

    @GET("v2/evolution-chain/{chainId}/")
    suspend fun getEvolutionChain(@Path("chainId") chainId: Int): EvolutionChainResponse
}