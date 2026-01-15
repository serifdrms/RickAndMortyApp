package com.isteserif.rickandmortyapp.api

import com.isteserif.rickandmortyapp.model.CharacterResponse // Birazdan oluşturacağız
import com.isteserif.rickandmortyapp.model.RickMortyCharacter
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    /**
     * Karakter listesini çeken fonksiyon.
     * Gideceği adres: BASE_URL + "character"
     *
     * @Query("page") -> URL'ye ?page=... ekler
     */
    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int
    ): Response<CharacterResponse> // Dönecek verinin tipi

    @GET("character/{id}")
    suspend fun getCharacter(
        @Path("id") id: Int // URL'deki {id}'nin yerine bu parametreyi koy
    ): Response<RickMortyCharacter> // NOT: Bu sefer tüm liste değil, SADECE TEK BİR karakter dönecek


}
