package de.htwk.musicmanager.data.modelclasses

import com.google.gson.annotations.SerializedName

data class Artist (
    val name: String,
    private val image : List<ImageInfo>
){

    val imageURL : String
        get() = image[1].url    //medium, sizes range from index 0 to 4


    data class ImageInfo(
        @SerializedName("#text")
        val url : String,
        private val size : String
    )
}