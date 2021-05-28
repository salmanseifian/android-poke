package com.salmanseifian.androidpoke.data_api.mapper

import com.salmanseifian.androidpoke.data.model.PokemonDetailsRepositoryModel
import com.salmanseifian.androidpoke.data_api.model.PokemonDetailsResponse

interface PokemonDetailsResponseToRepositoryModel {
    fun toRepositoryModel(pokemonDetailsResponse: PokemonDetailsResponse): PokemonDetailsRepositoryModel
}

class PokemonDetailsResponseToRepositoryModelImp() : PokemonDetailsResponseToRepositoryModel {

    override fun toRepositoryModel(pokemonDetailsResponse: PokemonDetailsResponse): PokemonDetailsRepositoryModel {
        return PokemonDetailsRepositoryModel(
            pokemonDetailsResponse.evolutionChain?.url,
            pokemonDetailsResponse.flavorTextEntries?.first { it.language?.name == "en" }?.flavorText,
            pokemonDetailsResponse.id,
            pokemonDetailsResponse.name

        )
    }

}