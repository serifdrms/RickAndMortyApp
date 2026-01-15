package com.isteserif.rickandmortyapp.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    // Rick and Morty API'nin ana adresi
    private const val BASE_URL = "https://rickandmortyapi.com/api/"

    // Hata ayıklama için (Opsiyonel ama ÇOK faydalı)
    // Bu, attığımız isteklerin ve gelen cevapların logcat'te (konsolda) görünmesini sağlar.
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // OkHttpClient'ı oluştur
    private val okHttpClient = OkHttpClient.Builder()
        // NOT: API Key'e gerek olmadığı için AuthInterceptor'a ihtiyacımız yok!
        .addInterceptor(loggingInterceptor) // Sadece Loglama görevlisini ekledik
        .build()

    // Retrofit objesini oluştur
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()) // JSON'u Kotlin'e çevir
        .build()

    /**
     * Menümüzü (ApiService) oluşturan ve dışarıya servis eden kısım.
     */
    val service: ApiService = retrofit.create(ApiService::class.java)
}