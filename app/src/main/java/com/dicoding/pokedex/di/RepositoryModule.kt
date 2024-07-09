package com.dicoding.pokedex.di

import com.dicoding.pokedex.data.repository.PokedexRespositoyImpl
import com.dicoding.pokedex.data.repository.SquadRepositoryImpl
import com.dicoding.pokedex.domain.repository.PokedexRepository
import com.dicoding.pokedex.domain.repository.SquadRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPokedexRepository(
        pokedexRespositoyImpl: PokedexRespositoyImpl
    ): PokedexRepository

    @Binds
    @Singleton
    abstract fun bindSquadRepository(
        squadRepositoryImpl: SquadRepositoryImpl
    ): SquadRepository
}