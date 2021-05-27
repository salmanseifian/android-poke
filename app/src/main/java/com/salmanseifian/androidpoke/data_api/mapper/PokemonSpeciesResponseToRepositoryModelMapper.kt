package com.salmanseifian.androidpoke.data_api.mapper

import com.salmanseifian.androidpoke.data.model.PokemonRepositoryModel
import com.salmanseifian.androidpoke.data.model.PokemonSpeciesRepositoryModel
import com.salmanseifian.androidpoke.data_api.model.PokemonSpeciesResponse
import javax.inject.Inject

interface PokemonSpeciesResponseToRepositoryModelMapper {
    fun toRepositoryModel(pokemonSpeciesResponse: PokemonSpeciesResponse): PokemonSpeciesRepositoryModel
}


class PokemonSpeciesResponseToRepositoryModelMapperImpl @Inject constructor() :
    PokemonSpeciesResponseToRepositoryModelMapper {

    override fun toRepositoryModel(pokemonSpeciesResponse: PokemonSpeciesResponse): PokemonSpeciesRepositoryModel {
        return PokemonSpeciesRepositoryModel(pokemonSpeciesResponse.pokemonSpecies.map {
            PokemonRepositoryModel(it.name, it.url)

        })
    }

}