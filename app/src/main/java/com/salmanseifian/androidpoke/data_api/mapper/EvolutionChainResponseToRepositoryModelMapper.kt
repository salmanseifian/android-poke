package com.salmanseifian.androidpoke.data_api.mapper

import com.salmanseifian.androidpoke.data.model.EvolutionChainRepositoryModel
import com.salmanseifian.androidpoke.data.model.PokemonSpeciesRepositoryModel
import com.salmanseifian.androidpoke.data_api.model.EvolutionChainResponse
import javax.inject.Inject

interface EvolutionChainResponseToRepositoryModelMapper {
    fun toRepositoryModel(evolutionChainResponse: EvolutionChainResponse): EvolutionChainRepositoryModel
}


class EvolutionChainResponseToRepositoryModelMapperImpl @Inject constructor() :
    EvolutionChainResponseToRepositoryModelMapper {

    override fun toRepositoryModel(evolutionChainResponse: EvolutionChainResponse): EvolutionChainRepositoryModel {
        val firstEvolution = evolutionChainResponse.chain?.evolvesTo?.first()?.species

        return EvolutionChainRepositoryModel(
            PokemonSpeciesRepositoryModel(
                firstEvolution?.name ?: "",
                firstEvolution?.url ?: ""
            )
        )
    }

}