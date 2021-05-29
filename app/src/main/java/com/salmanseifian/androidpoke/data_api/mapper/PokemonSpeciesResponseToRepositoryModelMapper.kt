package com.salmanseifian.androidpoke.data_api.mapper

import com.salmanseifian.androidpoke.data.model.SpeciesRepositoryModel
import com.salmanseifian.androidpoke.data_api.model.PokemonSpecies
import javax.inject.Inject

interface PokemonSpeciesResponseToRepositoryModelMapper {
    fun toRepositoryModel(pokemonSpecies: PokemonSpecies): SpeciesRepositoryModel
}


class PokemonSpeciesResponseToRepositoryModelMapperImpl @Inject constructor() :
    PokemonSpeciesResponseToRepositoryModelMapper {

    override fun toRepositoryModel(pokemonSpecies: PokemonSpecies) =
        SpeciesRepositoryModel(pokemonSpecies.name, pokemonSpecies.url)

}