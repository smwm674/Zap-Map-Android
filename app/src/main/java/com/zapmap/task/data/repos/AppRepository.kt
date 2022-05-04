package com.zapmap.task.data.repos

import com.zapmap.task.data.remote.RemoteDataSource
import com.zapmap.task.utils.NetworkOnly
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    fun getPokemonList(offset: Int, limit: Int) = NetworkOnly(
        networkCall = { remoteDataSource.getPokemonList(offset, limit) }
    )

    fun getPokemonDetail(id: String) = NetworkOnly(
        networkCall = { remoteDataSource.getPokemonDetail(id) }
    )

}