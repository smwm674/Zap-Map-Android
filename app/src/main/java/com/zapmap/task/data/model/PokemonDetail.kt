package com.zapmap.task.data.model

import com.google.gson.annotations.SerializedName

data class PokemonDetail(
    @SerializedName("name")
    val name: String,
    @SerializedName("weight")
    val weight: Float,
    @SerializedName("height")
    val height: Float,
    @SerializedName("sprites")
    val Sprites: Sprites,
    @SerializedName("types")
    val items: List<PokemonType>? = ArrayList()
)
