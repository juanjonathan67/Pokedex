package com.dicoding.pokedex.data.repository

import com.dicoding.pokedex.data.local.dao.PokemonSquadDao
import com.dicoding.pokedex.data.local.enitity.PokemonSquadEntity
import com.dicoding.pokedex.data.model.PokemonSquad
import com.dicoding.pokedex.data.model.PokemonStats
import com.dicoding.pokedex.domain.repository.SquadRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SquadRepositoryImpl @Inject constructor(
    private val pokemonSquadDao: PokemonSquadDao,
) : SquadRepository  {

    override suspend fun getAllPokemon(): List<PokemonSquad> {
        return withContext(Dispatchers.IO) {
            pokemonSquadDao.getAll().first().map { it.toDomainModel() }
        }
    }

    override suspend fun findPokemonById(id: Long): PokemonSquad {
        return withContext(Dispatchers.IO) {
            pokemonSquadDao.findById(id).first().toDomainModel()
        }
    }

    override suspend fun insertPokemon(pokemonSquad: List<PokemonSquad>): Int {
        val currentCount = getPokemonCount()
        if (currentCount >= 6) {
            return -1
        }

        val entities = pokemonSquad.map { it.toEntityModel() }
        return withContext(Dispatchers.IO) {
            pokemonSquadDao.insert(*entities.toTypedArray()).size
        }
    }


    override suspend fun deletePokemon(id: Long): Int {
        return withContext(Dispatchers.IO) {
            val entity = pokemonSquadDao.findById(id).first()
            pokemonSquadDao.delete(entity)
        }
    }

    override suspend fun toggleFavoritePokemon(id: Long): Boolean {
        return withContext(Dispatchers.IO) {
            val currentFavorite = pokemonSquadDao.findById(id).first().favorite
            pokemonSquadDao.updateFavoriteById(id, !currentFavorite)
            !currentFavorite
        }
    }

    override suspend fun isFavorite(id: Long): Boolean {
        return withContext(Dispatchers.IO) {
            pokemonSquadDao.findById(id).first().favorite
        }
    }

    override suspend fun getPokemonCount(): Int {
        return withContext(Dispatchers.IO) {
            pokemonSquadDao.count()
        }
    }

    private fun PokemonSquadEntity.toDomainModel(): PokemonSquad {
        return PokemonSquad(
            id = id,
            name = name,
            favorite = favorite,
            imageUrl = imageUrl,
            types = listOf(type1, type2).filter { it.isNotEmpty() },
            stats = PokemonStats(
                hp = hp,
                attack = attack,
                defense = defense,
                spAtt = spAtt,
                spDef = spDef,
                speed = speed
            )
        )
    }

    private fun PokemonSquad.toEntityModel(): PokemonSquadEntity {
        return PokemonSquadEntity(
            id = id,
            name = name,
            favorite = favorite,
            imageUrl = imageUrl,
            type1 = types.getOrElse(0) { "" },
            type2 = types.getOrElse(1) { "" },
            hp = stats.hp,
            attack = stats.attack,
            defense = stats.defense,
            spAtt = stats.spAtt,
            spDef = stats.spDef,
            speed = stats.speed
        )
    }

}