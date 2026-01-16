package com.isteserif.rickandmortyapp.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.isteserif.rickandmortyapp.api.ApiService
import com.isteserif.rickandmortyapp.model.RickMortyCharacter

class CharacterPagingSource(
    private val apiService: ApiService,
    private val name: String? = null
) : PagingSource<Int, RickMortyCharacter>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RickMortyCharacter> {
        val currentPage = params.key ?: 1

        return try {
            val response = apiService.getCharacters(page = currentPage, name = name)
            val characters = response.body()?.results ?: emptyList()

            LoadResult.Page(
                data = characters,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (characters.isEmpty()) null else currentPage + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, RickMortyCharacter>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}