package com.dicoding.pokedex.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dicoding.pokedex.data.remote.response.PokemonListItem
import com.dicoding.pokedex.data.remote.service.PokedexApiService
import javax.inject.Inject

class PokemonPagingSource @Inject constructor(
    private val pokedexApiService: PokedexApiService,
    private val searchQuery: String
) : PagingSource<Int, PokemonListItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonListItem> {
        return try {
            val offset = params.key ?: 0
            val response = pokedexApiService.getPokemonList(offset = offset, limit = params.loadSize)
            val items = response.results?.filterNotNull() ?: emptyList()
            val filteredData = items.filter {
                it.name?.contains(searchQuery, ignoreCase = true) == true
            }
            LoadResult.Page(
                data = filteredData,
                prevKey = if (offset == 0) null else offset - params.loadSize,
                nextKey = if (filteredData.isEmpty()) null else offset + params.loadSize
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PokemonListItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(20)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(20)
        }
    }
}