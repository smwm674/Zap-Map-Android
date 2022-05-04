package com.zapmap.task.ui.main_activity

import androidx.lifecycle.ViewModel
import com.zapmap.task.data.model.Pokemon
import com.zapmap.task.data.model.PokemonItem
import com.zapmap.task.data.repos.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private var repository: AppRepository,
) : ViewModel() {
    fun getPokemonList(offset: Int, limit: Int) = repository.getPokemonList(offset, limit)

    fun getPokemonDetail(id: String) = repository.getPokemonDetail(id)
}