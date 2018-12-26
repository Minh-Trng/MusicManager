package de.htwk.musicmanager.data.source.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.*
import com.google.gson.annotations.SerializedName
import de.htwk.musicmanager.data.modelclasses.ImageInfo
import java.lang.reflect.Type

@Entity(tableName = "album")
data class Album(
    @SerializedName("mbid")
    @PrimaryKey
    var id: String,
    var name : String,
    val image : List<ImageInfo>,    //val should be ignored by room, due to lack of setter
    var tracks : List<String> = emptyList()
){
    lateinit var artistName : String
    val imageURL : String
        get() = image[2].url    //medium, sizes range from index 0 to 3

    /**
     * Problem: depending on the request, the API returns "artist" either as String or JSONObject. This requires
     * either using 2 different Model-classes for "Album" or custom parsing. I went with the latter:
     * based on: https://stackoverflow.com/questions/28319473/gson-same-field-name-different-types
     */
    class AlbumDeserializer : JsonDeserializer<Album>{

        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Album {
            val album = gson.fromJson(json, Album::class.java)
            val jsonObject = json?.asJsonObject

            jsonObject?.let {
                val artist : Any = it.get("artist")
                when (artist) {
                    is String -> album.artistName = artist
                    is JsonObject -> album.artistName = artist.get("name").asString
                    else -> {
                        throw Exception("artist is neither String nor Object")
                    }
                }
            }
            return album
        }

        companion object {
            val gson = Gson()
        }

    }
}