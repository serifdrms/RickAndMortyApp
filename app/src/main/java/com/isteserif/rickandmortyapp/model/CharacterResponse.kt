package com.isteserif.rickandmortyapp.model

import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("info")
    val info: Info?, // Info sınıfımızı burada kullandık
    @SerializedName("results")
    val results: List<RickMortyCharacter>? // Karakter listesi
)