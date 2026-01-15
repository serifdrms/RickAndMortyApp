package com.isteserif.rickandmortyapp.model

import com.google.gson.annotations.SerializedName

data class RickMortyCharacter(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("species")
    val species: String?,
    @SerializedName("image")
    val image: String?,

    // --- YENİ EKLENEN 3 ALAN ---
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("origin")
    val origin: Origin?, // Az önce oluşturduğumuz Origin sınıfı
    @SerializedName("location")
    val location: Location? // Az önce oluşturduğumuz Location sınıfı
)