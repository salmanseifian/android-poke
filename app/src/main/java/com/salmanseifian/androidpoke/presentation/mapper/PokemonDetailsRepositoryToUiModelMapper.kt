package com.salmanseifian.androidpoke.presentation.mapper

import com.salmanseifian.androidpoke.data.model.PokemonDetailsRepositoryModel
import com.salmanseifian.androidpoke.presentation.model.PokemonDetailsUiModel

interface PokemonDetailsRepositoryToUiModelMapper {
    fun toRepositoryModel(pokemonDetailsRepositoryModel: PokemonDetailsRepositoryModel): PokemonDetailsUiModel
}

class PokemonDetailsRepositoryToUiModelMapperImpl : PokemonDetailsRepositoryToUiModelMapper {

    override fun toRepositoryModel(pokemonDetailsRepositoryModel: PokemonDetailsRepositoryModel): PokemonDetailsUiModel {
        return PokemonDetailsUiModel(
            pokemonDetailsRepositoryModel.evolutionChainUrl,
            pokemonDetailsRepositoryModel.flavorTextEntries,
            pokemonDetailsRepositoryModel.id,
            pokemonDetailsRepositoryModel.name
        )
    }

}