package com.dicoding.pokedex.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.dicoding.pokedex.data.model.PokemonGrid
import com.dicoding.pokedex.data.remote.response.PokemonDetailResponse
import com.dicoding.pokedex.data.remote.response.PokemonListItem
import com.dicoding.pokedex.domain.repository.PokedexRepository
import com.dicoding.pokedex.domain.repository.SquadRepository
import com.dicoding.pokedex.utils.toTitleCaseOrNull
import com.dicoding.pokedex.utils.typeToColor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pokedexRepository: PokedexRepository,
    private val squadRepository: SquadRepository,
): ViewModel() {
    private val searchQueryFlow = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val pokemonPagingFlow = searchQueryFlow.flatMapLatest { query ->
        Pager(
            config = PagingConfig(
                pageSize = 20,
            ),
        ) {
            pokedexRepository.getPokemonPagingSource(query)
        }.flow
            .map { pagingData ->
                pagingData.map { pokemonListItem ->
                    getPokemonGrid(pokemonListItem)
                }
            }
            .cachedIn(viewModelScope)
    }

    private suspend fun getPokemonGrid(pokemonListItem: PokemonListItem): PokemonGrid {
        val details = getPokemonDetail(pokemonListItem.url ?: "")
        val id = details.id.toString()
        return PokemonGrid(
            id = id,
            name = details.name.toTitleCaseOrNull(),
            bgColor = typeToColor(details.types?.first()?.type?.name.toString()),
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${details.id}.png",
            favorite = isPokemonInSquad(id.toLong())
        )
    }

    private suspend fun getPokemonDetail(url: String): PokemonDetailResponse {
        val id = extractIdFromUrl(url)
        return pokedexRepository.getPokemonDetail(id).first()
    }

    private suspend fun isPokemonInSquad(id: Long): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                squadRepository.findPokemonById(id)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    private fun extractIdFromUrl(url: String): Long {
        return url.trimEnd('/').substringAfterLast('/').toLong()
    }

    fun setSearchQuery(query: String) {
        searchQueryFlow.value = query
    }
}