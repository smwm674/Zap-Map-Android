package com.zapmap.task.data.model

import com.google.gson.annotations.SerializedName

data class PokemonType(

    @SerializedName("type")
    val type: PokemonItem,
)
