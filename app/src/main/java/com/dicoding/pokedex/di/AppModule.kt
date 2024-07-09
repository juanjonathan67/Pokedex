package com.dicoding.pokedex.di

import android.content.Context
import androidx.room.Room
import com.dicoding.pokedex.BuildConfig
import com.dicoding.pokedex.data.local.database.PokemonSquadDatabase
import com.dicoding.pokedex.data.remote.service.PokedexApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePokedexApiService(): PokedexApiService {
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(PokedexApiService::class.java)
    }

    @Singleton
    @Provides
    fun providePokemonSquadDatabase(
        @ApplicationContext appContext: Context
    ): PokemonSquadDatabase {
        return Room
            .databaseBuilder(
                appContext,
                PokemonSquadDatabase::class.java,
                "pokemon_squad_db"
            )
            .build()
    }

    @Singleton
    @Provides
    fun providePokemonSquadDao(
        pokemonSquadDatabase: PokemonSquadDatabase
    ) = pokemonSquadDatabase.pokemonSquadDao()
}