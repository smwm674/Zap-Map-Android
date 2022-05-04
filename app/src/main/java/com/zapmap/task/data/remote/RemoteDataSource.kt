package com.zapmap.task.data.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val appService: AppService
) : BaseDataSource() {

    suspend fun getPokemonList(offset: Int, limit: Int) =
        getResult { appService.getPokemonList(offset, limit) }

    suspend fun getPokemonDetail(id: String) = getResult {
        appService.getPokemonDetail(id) }

}