package de.htwk.musicmanager.data.source.database.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.*
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import de.htwk.musicmanager.data.modelclasses.ImageInfo
import de.htwk.musicmanager.data.modelclasses.Track
import java.lang.reflect.Type

@Entity(tableName = "album")
class Album(
    @SerializedName("mbid")
    @PrimaryKey
    var id: String = "",
    var name : String = "",
    @Ignore
    val image : List<ImageInfo> = emptyList(),
    var artistName : String = "",
    var tracks : List<Track> = emptyList()
) {

    val imageURL : String
        get() = if(image.size>0) image[2].url else "invalidPath"    //medium, sizes range from index 0 to 3

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
                    is JsonPrimitive -> album.artistName = artist.toString().replace("\"", "")
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

    class TracksConverter{
        @TypeConverter
        fun fromTracks(tracks: List<Track>) = Gson().toJson(tracks)

        @TypeConverter
        fun toGraphPoints(json: String) : List<Track>{
            val type = object : TypeToken<List<Track>>(){}.type
            return Gson().fromJson(json, type)
        }
    }
}