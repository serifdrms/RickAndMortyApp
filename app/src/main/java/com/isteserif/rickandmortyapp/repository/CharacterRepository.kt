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

    // paging icin ana nesnemizi olusturalim
    fun getCharacters(name: String? = null): Flow<PagingData<RickMortyCharacter>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CharacterPagingSource(apiService, name) } //cha.pag.sou.connected
        ).flow
    }
    
    suspend fun getCharacter(id: Int): Response<RickMortyCharacter> {
        return apiService.getCharacter(id)
    }
}