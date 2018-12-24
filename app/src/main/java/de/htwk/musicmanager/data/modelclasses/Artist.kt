package de.htwk.musicmanager.data.modelclasses

import com.google.gson.annotations.SerializedName

data class Artist (
    val name: String,
    val image : List<ImageInfo>
){
    data class ImageInfo(
        @SerializedName("#text")
        val url : String,
        private val size : String
    ){

    }

//    enum class ImageSize(size: String){
//        SMALL,
//        MEDIUM,
//        LARGE,
//        EXTRALARGE,
//        MEGA
//    }
}