package com.zapmap.task.data.model

import com.google.gson.annotations.SerializedName

data class PokemonItem(

    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)
