package com.salmanseifian.androidpoke.data_api.model


import com.google.gson.annotations.SerializedName

data class PokemonDetailsResponse(
    @SerializedName("evolution_chain")
    val evolutionChain: EvolutionChain?,
    @SerializedName("flavor_text_entries")
    val flavorTextEntries: List<FlavorTextEntry>?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
)

data class EvolutionChain(
    @SerializedName("url")
    val url: String?
)

data class FlavorTextEntry(
    @SerializedName("flavor_text")
    val flavorText: String?,
    @SerializedName("language")
    val language: Language?,
    @SerializedName("version")
    val version: Version?
)

data class Language(
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?
)

data class Version(
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?
)