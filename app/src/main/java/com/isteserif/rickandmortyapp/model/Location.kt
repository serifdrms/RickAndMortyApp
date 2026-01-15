package com.isteserif.rickandmortyapp.model

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("name")
    val name: String?
)