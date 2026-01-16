package com.isteserif.rickandmortyapp.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    // Rick and Morty API'nin ana adresi
    private const val BASE_URL = "https://rickandmortyapi.com/api/"

    // Bu aşağısı, attığımız isteklerin ve gelen cevapların logcat'te (konsolda) görünmesini sağlıyo.
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
     ApiService arayüzünü yani inteface'yi Retrofit ile canlandırıp projemizde kullanılabilir hale getirdim.*/

    val service: ApiService = retrofit.create(ApiService::class.java)
}
/** Retrofit bildiğimiz üzere OkHttpClient üzerine kurulu bi kütüphane
 * İleride bir API KEY veyahut Token vs. gibi bir güvenlik önlemi eklenmesi
 * Gerekirse kurmuş Olduğum bu yapı sayesinde OkHttpClient İçinden Kolayca
 * gereken işlemler sağlanabilir*/