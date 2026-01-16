package com.isteserif.rickandmortyapp.model

import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("info")
    val info: Info?, // Info sınıfımızı burada kullandık
    @SerializedName("results")
    val results: List<RickMortyCharacter>? // Karakter listesi
)

 /** API den paketimiz gelince info yani kaç sayfa olduğu bilgisi
  * ile results yani karakterlerin listesi geliyor
  */
//Yani bi bakıma CharacterResponse sınıfımız iki ana parçayı bir arada tutuyor.