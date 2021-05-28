package com.salmanseifian.androidpoke.data.model


data class PokemonDetailsRepositoryModel(
    val evolutionChain: EvolutionChainDetailsRepositoryModel?,
    val flavorTextEntries: List<FlavorTextEntryRepositoryModel>?,
    val id: Int?,
    val name: String?,
)

data class EvolutionChainDetailsRepositoryModel(
    val url: String?
)

data class FlavorTextEntryRepositoryModel(
    val flavorText: String?,
    val language: LanguageRepositoryModel?,
    val version: VersionRepositoryModel?
)

data class LanguageRepositoryModel(
    val name: String?,
    val url: String?
)

data class VersionRepositoryModel(
    val name: String?,
    val url: String?
)