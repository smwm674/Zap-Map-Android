package com.zapmap.task.data.model

import com.google.gson.annotations.SerializedName


data class Pokemon(
    @SerializedName("count")
    val count: Int,
    @SerializedName("results")
    val items: ArrayList<PokemonItem>? = ArrayList()
)

