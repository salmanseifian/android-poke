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

        var chain = evolutionChainResponse.chain

        val pairList = mutableListOf<Pair<SpeciesRepositoryModel, SpeciesRepositoryModel>>()

        while (chain != null && !chain.evolvesTo.isNullOrEmpty()) {
            pairList.add(
                Pair(
                    SpeciesRepositoryModel(chain.species?.name ?: "", chain.species?.url ?: ""),
                    SpeciesRepositoryModel(
                        chain.evolvesTo!![0].species?.name ?: "",
                        chain.evolvesTo!![0].species?.url ?: ""
                    )
                )
            )
            chain = chain.evolvesTo?.get(0)
        }

        return EvolutionChainRepositoryModel(pairList)
    }

}