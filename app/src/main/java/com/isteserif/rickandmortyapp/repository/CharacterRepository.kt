package com.isteserif.rickandmortyapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.isteserif.rickandmortyapp.api.ApiClient
import com.isteserif.rickandmortyapp.model.RickMortyCharacter
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class CharacterRepository {

    private val apiService = ApiClient.service

    fun getCharacters(): Flow<PagingData<RickMortyCharacter>> {

        return Pager(
            config = PagingConfig(
                pageSize = 20, // Her sayfada API 20 veri döndürüyor
                enablePlaceholders = false
            ),
            // PagingSource'umuzu burada belirtiyoruz.
            pagingSourceFactory = { CharacterPagingSource(apiService) }
        ).flow
    }
    
    suspend fun getCharacter(id: Int): Response<RickMortyCharacter> {
        return apiService.getCharacter(id)
    }
    
}