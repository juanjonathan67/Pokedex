package com.dicoding.pokedex.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.pokedex.data.model.PokemonAbilities
import com.dicoding.pokedex.data.model.PokemonDetail
import com.dicoding.pokedex.data.model.PokemonEvoChart
import com.dicoding.pokedex.data.model.PokemonMoves
import com.dicoding.pokedex.data.model.PokemonStats
import com.dicoding.pokedex.data.remote.response.AbilitiesItem
import com.dicoding.pokedex.data.remote.response.Chain
import com.dicoding.pokedex.data.remote.response.MovesItem
import com.dicoding.pokedex.data.remote.response.PokemonEvoChainResponse
import com.dicoding.pokedex.data.remote.response.PokemonMoveResponse
import com.dicoding.pokedex.data.remote.response.PokemonSpeciesResponse
import com.dicoding.pokedex.data.remote.response.StatsItem
import com.dicoding.pokedex.data.remote.response.TypesItem
import com.dicoding.pokedex.domain.repository.PokedexRepository
import com.dicoding.pokedex.domain.repository.SquadRepository
import com.dicoding.pokedex.utils.UiState
import com.dicoding.pokedex.utils.capitalizeSentencesOrNull
import com.dicoding.pokedex.utils.toTitleCaseOrNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val pokedexRepository: PokedexRepository,
    private val squadRepository: SquadRepository,
): ViewModel() {
    private val _pokemonDetail: MutableStateFlow<UiState<PokemonDetail>> = MutableStateFlow(UiState.Loading)
    val pokemonDetail: StateFlow<UiState<PokemonDetail>> get() = _pokemonDetail

    fun getPokemonDetail(id: Long) {
        viewModelScope.launch {
            try {
                pokedexRepository.getPokemonDetail(id)
                    .catch {
                        _pokemonDetail.value = UiState.Error(it.message.toString())
                    }
                    .collect { pokemonDetailResponse ->
                        val pokemonSpecies = pokemonDetailResponse.species?.url?.let { getPokemonSpecies(it) }

                        val pokemonDetail = PokemonDetail(
                            id = pokemonDetailResponse.id.toString().toLong(),
                            name = pokemonDetailResponse.name.toTitleCaseOrNull(),
                            favorite = isPokemonInSquad(id),
                            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemonDetailResponse.id}.png",
                            genus = pokemonSpecies?.genera?.find { it?.language?.name == "en" }?.genus.toString(),
                            types = generateTypes(pokemonDetailResponse.types),
                            description = pokemonSpecies?.flavorTextEntries?.find { it?.language?.name == "en" }?.flavorText.capitalizeSentencesOrNull(),
                            abilities = generatePokemonAbility(pokemonDetailResponse.abilities),
                            stats = generatePokemonStats(pokemonDetailResponse.stats),
                            evoChart = generateEvoChart(pokemonSpecies?.evolutionChain?.url ?: "https://pokeapi.co/api/v2/evolution-chain/1/"),
                            moves = generateMoves(pokemonDetailResponse.moves)
                        )

                        _pokemonDetail.value = UiState.Success(pokemonDetail)
                    }
            } catch (e: Exception) {
                _pokemonDetail.value = UiState.Error(e.message.toString())
            }
        }
    }

    private suspend fun getPokemonSpecies(url: String): PokemonSpeciesResponse {
        return withContext(Dispatchers.IO) {
            try {
                val id = extractIdFromUrl(url)
                val response = pokedexRepository.getPokemonSpecies(id).first()
                return@withContext response
            } catch (e: Exception) {
                throw Exception(e.message.toString())
            }
        }
    }

    private suspend fun getPokemonEvoChain(url: String): PokemonEvoChainResponse {
        return withContext(Dispatchers.IO) {
            try {
                val id = extractIdFromUrl(url)
                val response = pokedexRepository.getPokemonEvoChain(id).first()
                return@withContext response
            } catch (e: Exception) {
                throw Exception(e.message.toString())
            }
        }
    }

    private suspend fun getPokemonMove(url: String): PokemonMoveResponse {
        return withContext(Dispatchers.IO) {
            try {
                val id = extractIdFromUrl(url)
                val response = pokedexRepository.getPokemonMove(id).first()
                return@withContext response
            } catch (e: Exception) {
                throw Exception(e.message.toString())
            }
        }
    }

    private fun generateTypes(types: List<TypesItem?>?): List<String> {
        return types?.mapNotNull { typesItem ->
            typesItem?.type?.name.toString()
        } ?: emptyList()
    }

    private fun generatePokemonAbility(abilities: List<AbilitiesItem?>?): PokemonAbilities {
        val nonNullItems = abilities?.filterNotNull() ?: emptyList()
        val hiddenAbility = nonNullItems.find { it.isHidden == true }?.ability?.name
        val nonHiddenAbilities = nonNullItems.filter { it.isHidden != true }.mapNotNull { it.ability?.name }

        return PokemonAbilities(
            ability1 = nonHiddenAbilities.getOrElse(0) { "" },
            ability2 = nonHiddenAbilities.getOrNull(1),
            abilityHidden = hiddenAbility ?: ""
        )
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

    private suspend fun generateEvoChart(evoChartUrl: String): List<PokemonEvoChart> {
        val pokemonEvoChain = getPokemonEvoChain(evoChartUrl)
        return pokemonEvoChain.chain?.let { traverseEvolutionChain(it) } ?: emptyList()
    }

    private suspend fun generateMoves(moves: List<MovesItem?>?): List<PokemonMoves> {
        return moves?.filter {
            it?.versionGroupDetails?.firstOrNull()?.moveLearnMethod?.name == "level-up"
        }?.mapNotNull { movesItem ->
            val pokemonMove = movesItem?.move?.url?.let { getPokemonMove(it) }

            PokemonMoves(
                level = movesItem?.versionGroupDetails?.firstOrNull()?.levelLearnedAt?.toString() ?: "0",
                name = movesItem?.move?.name.toTitleCaseOrNull(),
                type = pokemonMove?.type?.name.toString(),
                power = pokemonMove?.power?.toString() ?: "-"
            )
        }?.sortedBy { it.level.toIntOrNull() } ?: emptyList()
    }

    private suspend fun traverseEvolutionChain(chain: Chain): List<PokemonEvoChart> {
        val evoCharts = mutableListOf<PokemonEvoChart>()

        suspend fun processChain(chain: Chain?) {
            if (chain?.species?.name == null) return

            val speciesName = chain.species.name
            val pokemonDetails = pokedexRepository.getPokemonDetail(speciesName).first()
            val pokemonTypes = generateTypes(pokemonDetails.types)
            val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemonDetails.id}.png"
            val evoDetails = chain.evolvesTo?.firstOrNull()?.evolutionDetails?.firstOrNull()
            val triggerName = evoDetails?.trigger?.name

            val evoBy = StringBuilder()

            if (triggerName != null) {
                evoBy.append(triggerName.toTitleCaseOrNull())

                if (triggerName == "level-up" && evoDetails.minLevel != null) {
                    evoBy.append(": ${evoDetails.minLevel}")
                } else if(triggerName == "min-happiness") {
                    evoBy.append(": ${evoDetails.minHappiness}")
                } else if(triggerName == "min-beauty") {
                    evoBy.append(": ${evoDetails.minBeauty}")
                } else if(triggerName == "min-affection") {
                    evoBy.append(": ${evoDetails.minAffection}")
                } else if(triggerName == "use-item") {
                    evoBy.append(": ${evoDetails.item?.name.toTitleCaseOrNull()}")
                } else if(triggerName == "held-item") {
                    evoBy.append(": ${evoDetails.heldItem?.name.toTitleCaseOrNull()}")
                } else if(triggerName == "known-move") {
                    evoBy.append(": ${evoDetails.knownMove?.name.toTitleCaseOrNull()}")
                }
            }

            evoCharts.add(
                PokemonEvoChart(
                    id = pokemonDetails.id.toString().toLong(),
                    name = speciesName.toTitleCaseOrNull(),
                    imageUrl = imageUrl,
                    types = pokemonTypes,
                    evoBy = if (evoBy.isEmpty()) null else evoBy.toString()
                )
            )

            chain.evolvesTo?.forEach { evolvesToItem ->
                processChain(evolvesToItem?.species?.let { Chain(evolvesToItem.evolutionDetails, it, evolvesToItem.evolvesTo, evolvesToItem.isBaby) },)
            }
        }

        processChain(chain)
        return evoCharts
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
}