package com.salmanseifian.androidpoke.data_api.mapper

import com.salmanseifian.androidpoke.data.model.PokemonSpeciesRepositoryModel
import com.salmanseifian.androidpoke.data_api.model.PokemonSpecies
import javax.inject.Inject

interface PokemonSpeciesResponseToRepositoryModelMapper {
    fun toRepositoryModel(pokemonSpecies: PokemonSpecies): PokemonSpeciesRepositoryModel
}


class PokemonSpeciesResponseToRepositoryModelMapperImpl @Inject constructor() :
    PokemonSpeciesResponseToRepositoryModelMapper {

    override fun toRepositoryModel(pokemonSpecies: PokemonSpecies) =
        PokemonSpeciesRepositoryModel(pokemonSpecies.name, pokemonSpecies.url)

}