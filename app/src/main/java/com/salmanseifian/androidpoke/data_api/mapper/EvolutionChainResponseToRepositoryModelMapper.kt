package com.salmanseifian.androidpoke.data_api.mapper

import com.salmanseifian.androidpoke.data.model.EvolutionChainRepositoryModel
import com.salmanseifian.androidpoke.data.model.SpeciesRepositoryModel
import com.salmanseifian.androidpoke.data_api.model.EvolutionChainResponse
import javax.inject.Inject

interface EvolutionChainResponseToRepositoryModelMapper {
    fun toRepositoryModel(evolutionChainResponse: EvolutionChainResponse): EvolutionChainRepositoryModel
}


class EvolutionChainResponseToRepositoryModelMapperImpl @Inject constructor() :
    EvolutionChainResponseToRepositoryModelMapper {

    override fun toRepositoryModel(evolutionChainResponse: EvolutionChainResponse): EvolutionChainRepositoryModel {
        val firstSpecies = evolutionChainResponse.chain?.species
        val firstEvolution = evolutionChainResponse.chain?.evolvesTo?.first()?.species

        val secondSpecies =
            evolutionChainResponse.chain?.evolvesTo?.first()?.species
        val secondEvolution =
            evolutionChainResponse.chain?.evolvesTo?.first()?.evolvesTo?.first()?.species

        return EvolutionChainRepositoryModel(
            listOf(
                Pair(
                    SpeciesRepositoryModel(firstSpecies?.name ?: "", firstSpecies?.url ?: ""),
                    SpeciesRepositoryModel(firstEvolution?.name ?: "", firstEvolution?.url ?: "")
                ),
                Pair(
                    SpeciesRepositoryModel(secondSpecies?.name ?: "", secondSpecies?.url ?: ""),
                    SpeciesRepositoryModel(secondEvolution?.name ?: "", secondEvolution?.url ?: "")
                )
            )
        )
    }

}