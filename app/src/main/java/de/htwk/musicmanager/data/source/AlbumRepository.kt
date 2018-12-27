package de.htwk.musicmanager.data.source

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import com.google.gson.GsonBuilder
import de.htwk.musicmanager.data.modelclasses.Artist
import de.htwk.musicmanager.data.source.database.AlbumDatabase
import de.htwk.musicmanager.data.source.database.daos.AlbumDao
import de.htwk.musicmanager.data.source.database.entities.Album
import de.htwk.musicmanager.data.source.network.LastFMService
import de.htwk.musicmanager.data.source.network.UnwrapInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AlbumRepository private constructor(c: Context): Repository {



    private var lastFMService: LastFMService
    private var albumDao: AlbumDao

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

        albumDao = AlbumDatabase.getInstance(c).albumDao()
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

    override fun searchAlbum(albumID: String, update: (album: Album?) -> Unit) {
        val album = albumDao.getAlbumByID(albumID)
        if(album.value == null){

            val options = HashMap<String, String>()
            options["mbid"] = albumID
            options["method"] = "album.getinfo"
            options["api_key"] = LastFMService.API_KEY
            options["format"] = "json"

            lastFMService.getAlbumDetails(options).enqueue(object : Callback<Album>{
                override fun onFailure(call: Call<Album>, t: Throwable) {
                    update(null)
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<Album>, response: Response<Album>) {
                    if(response.body() != null){
                        update(response.body())
                    }else{
                        update(null)
                    }
                }
            })

        }else{
            update(album.value)
        }

    }

    override fun getStoredAlbums(): LiveData<List<Album>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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