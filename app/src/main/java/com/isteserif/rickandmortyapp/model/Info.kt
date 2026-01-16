package com.isteserif.rickandmortyapp.model

import com.google.gson.annotations.SerializedName

data class Info(
    @SerializedName("count") // toplam karakter sayısı
    val count: Int?,
    @SerializedName("pages")
    val pages: Int?,
    @SerializedName("next") // paging3 kütpanesi bak burda işe yariyor
    val next: String?,
    @SerializedName("prev")
    val prev: String?
)