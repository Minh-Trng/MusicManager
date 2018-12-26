package de.htwk.musicmanager.data.modelclasses

import com.google.gson.annotations.SerializedName

data class ImageInfo(
    @SerializedName("#text")
    val url : String,
    private val size : String
)