package com.isteserif.rickandmortyapp.api

import com.isteserif.rickandmortyapp.model.CharacterResponse
import com.isteserif.rickandmortyapp.model.RickMortyCharacter
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// retrofit kullanarak appi endpointlerini tanımlıyoruz
interface ApiService {

    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int,
        @Query("name") name: String? = null
    ): Response<CharacterResponse>

    @GET("character/{id}")
    suspend fun getCharacter(
        @Path("id") id: Int
    ): Response<RickMortyCharacter>
}