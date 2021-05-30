package com.salmanseifian.androidpoke.presentation.mapper

import com.salmanseifian.androidpoke.data.model.EvolutionChainRepositoryModel
import com.salmanseifian.androidpoke.presentation.model.EvolutionChainUiModel
import com.salmanseifian.androidpoke.presentation.model.SpeciesUiModel
import javax.inject.Inject

interface EvolutionChainRepositoryToUiModelMapper {
    fun toUiModel(evolutionChainRepositoryModel: EvolutionChainRepositoryModel): EvolutionChainUiModel
}


class EvolutionChainRepositoryToUiModelMapperImpl @Inject constructor() :
    EvolutionChainRepositoryToUiModelMapper {

    override fun toUiModel(evolutionChainRepositoryModel: EvolutionChainRepositoryModel): EvolutionChainUiModel {

        return EvolutionChainUiModel(evolutionChainRepositoryModel.evolutions?.map {
            Pair(
                SpeciesUiModel(it.first?.name ?: "", it.first?.url ?: ""),
                SpeciesUiModel(it.second?.name ?: "", it.second?.url ?: "")
            )
        })
    }

}