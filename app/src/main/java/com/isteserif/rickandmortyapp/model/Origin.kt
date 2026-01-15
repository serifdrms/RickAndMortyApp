package com.isteserif.rickandmortyapp.model

import com.google.gson.annotations.SerializedName

data class Origin(
    @SerializedName("name")
    val name: String?
)