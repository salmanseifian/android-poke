package com.salmanseifian.androidpoke.presentation.mapper

import com.salmanseifian.androidpoke.data.model.SpeciesRepositoryModel
import com.salmanseifian.androidpoke.presentation.model.SpeciesUiModel
import javax.inject.Inject

interface PokemonSpeciesRepositoryToUiModelMapper {
    fun toRepositoryModel(speciesRepositoryModel: SpeciesRepositoryModel): SpeciesUiModel
}


class PokemonSpeciesRepositoryToUiModelMapperImp @Inject constructor() :
    PokemonSpeciesRepositoryToUiModelMapper {

    override fun toRepositoryModel(speciesRepositoryModel: SpeciesRepositoryModel) =
        SpeciesUiModel(speciesRepositoryModel.name, speciesRepositoryModel.url)

}