package com.dicoding.pokedex.ui.screen.squad

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.pokedex.data.model.PokemonSquad
import com.dicoding.pokedex.data.model.PokemonStats
import com.dicoding.pokedex.data.remote.response.StatsItem
import com.dicoding.pokedex.data.remote.response.TypesItem
import com.dicoding.pokedex.domain.repository.PokedexRepository
import com.dicoding.pokedex.domain.repository.SquadRepository
import com.dicoding.pokedex.utils.UiState
import com.dicoding.pokedex.utils.toTitleCaseOrNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SquadViewModel @Inject constructor(
    private val squadRepository: SquadRepository,
    private val pokedexRepository: PokedexRepository,
): ViewModel() {
    private val _pokemonSquad: MutableStateFlow<UiState<List<PokemonSquad>>> = MutableStateFlow(UiState.Loading)
    val pokemonSquad: StateFlow<UiState<List<PokemonSquad>>> get() =  _pokemonSquad

    init {
        getAllPokemon()
    }

    fun getAllPokemon() {
        viewModelScope.launch {
            _pokemonSquad.value = UiState.Success(squadRepository.getAllPokemon())
        }
    }

    suspend fun toggleFavoriteStatus(id: Long) {
        viewModelScope.launch {
            val isFavorite = isPokemonInSquad(id)
            if (isFavorite) {
                squadRepository.deletePokemon(id)
            } else {
                val details = pokedexRepository.getPokemonDetail(id).first()

                squadRepository.insertPokemon(
                    listOf(
                        PokemonSquad(
                            id = id,
                            name = details.name.toTitleCaseOrNull(),
                            favorite = true,
                            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${details.id}.png",
                            types = generateTypes(details.types),
                            stats = generatePokemonStats(details.stats)
                        )
                    )
                )
            }
        }
    }

    suspend fun getPokemonCount(): Flow<Int> {
        return flow {
            emit(squadRepository.getPokemonCount())
        }
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

    private fun generateTypes(types: List<TypesItem?>?): List<String> {
        return types?.mapNotNull { typesItem ->
            typesItem?.type?.name.toString()
        } ?: emptyList()
    }

    private fun generatePokemonStats(stats: List<StatsItem?>?): PokemonStats {
        val nonNullItems = stats?.filterNotNull() ?: emptyList()

        return PokemonStats(
            hp = nonNullItems.find {
                it.stat?.name == "hp"
            }?.baseStat.toString(),
            attack = nonNullItems.find {
                it.stat?.name == "attack"
            }?.baseStat.toString(),
            defense = nonNullItems.find {
                it.stat?.name == "defense"
            }?.baseStat.toString(),
            spAtt = nonNullItems.find {
                it.stat?.name == "special-attack"
            }?.baseStat.toString(),
            spDef = nonNullItems.find {
                it.stat?.name == "special-defense"
            }?.baseStat.toString(),
            nonNullItems.find {
                it.stat?.name == "speed"
            }?.baseStat.toString(),
        )
    }
}