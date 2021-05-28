package com.salmanseifian.androidpoke.data_api.mapper

import com.salmanseifian.androidpoke.data.model.PokemonDetailsRepositoryModel
import com.salmanseifian.androidpoke.data_api.model.PokemonDetailsResponse

interface PokemonDetailsResponseToRepositoryModelMapper {
    fun toRepositoryModel(pokemonDetailsResponse: PokemonDetailsResponse): PokemonDetailsRepositoryModel
}

class PokemonDetailsResponseToRepositoryModelMapperImp : PokemonDetailsResponseToRepositoryModelMapper {

    override fun toRepositoryModel(pokemonDetailsResponse: PokemonDetailsResponse): PokemonDetailsRepositoryModel {
        return PokemonDetailsRepositoryModel(
            pokemonDetailsResponse.evolutionChain?.url,
            pokemonDetailsResponse.flavorTextEntries?.first { it.language?.name == "en" }?.flavorText,
            pokemonDetailsResponse.id,
            pokemonDetailsResponse.name

        )
    }

}