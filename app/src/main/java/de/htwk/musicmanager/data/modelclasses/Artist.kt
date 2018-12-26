package de.htwk.musicmanager.data.modelclasses

data class Artist (
    val name: String,
    private val image : List<ImageInfo> = emptyList()
){

    val imageURL : String
        get() = image[1].url    //medium, sizes range from index 0 to 4



}