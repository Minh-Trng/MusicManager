package de.htwk.musicmanager.data.source

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import com.google.gson.GsonBuilder
import de.htwk.musicmanager.data.modelclasses.Artist
import de.htwk.musicmanager.data.source.database.entities.Album
import de.htwk.musicmanager.data.source.network.LastFMService
import de.htwk.musicmanager.data.source.network.UnwrapInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AlbumRepository private constructor(c: Context): Repository {

    override fun getStoredAlbums(): LiveData<List<Album>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private lateinit var lastFMService: LastFMService

    init {

        val gson = GsonBuilder()
            .registerTypeAdapter(Album::class.java, Album.AlbumDeserializer())
            .create()

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(UnwrapInterceptor())
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://ws.audioscrobbler.com")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

        lastFMService = retrofit.create(LastFMService::class.java)
    }

    override fun searchArtists(name: String, callback: Callback<List<Artist>>) {

        val options = HashMap<String, String>()
        options["artist"] = name
        options["method"] = "artist.search"
        options["api_key"] = LastFMService.API_KEY
        options["format"] = "json"

        lastFMService.searchArtists(options).enqueue(callback)
    }


    override fun searchAlbums(artistName: String, callback: Callback<List<Album>>) {
        val options = HashMap<String, String>()
        options["artist"] = artistName
        options["method"] = "artist.gettopalbums"
        options["api_key"] = LastFMService.API_KEY
        options["format"] = "json"

        lastFMService.searchAlbums(options).enqueue(callback)
    }

    /**
     * The Singleton-Implementation is based on this sample:
     * https://github.com/googlesamples/android-architecture-components/blob/master/BasicRxJavaSampleKotlin
     * /app/src/main/java/com/example/android/observability/persistence/UsersDatabase.kt
     */
    companion object {

        @Volatile
        var instance: AlbumRepository? = null

        fun getInstance(application: Application): AlbumRepository =
                instance ?: synchronized(this)  {
                    instance ?: AlbumRepository(application).also { instance = it }
                }
    }
}