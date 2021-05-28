package com.salmanseifian.androidpoke.data_api.model


import com.google.gson.annotations.SerializedName

data class EvolutionChainResponse(
    @SerializedName("chain")
    val chain: Chain?,
    @SerializedName("id")
    val id: Int?
)

data class Chain(
    @SerializedName("evolves_to")
    val evolvesTo: List<EvolvesTo>?,
)

data class EvolvesTo(
    @SerializedName("is_baby")
    val isBaby: Boolean?,
    @SerializedName("species")
    val species: Species?
)

data class Species(
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?
)