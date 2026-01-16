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

    // Bunları sonradan ekledin UNUTMA ! Revizyonu YAP !!
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("origin")
    val origin: Origin?,
    @SerializedName("location")
    val location: Location?
)