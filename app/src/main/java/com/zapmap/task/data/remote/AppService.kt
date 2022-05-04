package com.zapmap.task.data.remote

import com.zapmap.task.data.constant.URLHelper
import com.zapmap.task.data.model.Pokemon
import com.zapmap.task.data.model.PokemonDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AppService {

    @GET(URLHelper.pokemonList)
    suspend fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<Pokemon>

    @GET("${URLHelper.pokemonList}/{id}")
    suspend fun getPokemonDetail(
        @Path("id") name: String
    ): Response<PokemonDetail>

}