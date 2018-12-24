package de.htwk.musicmanager.data.source

import android.content.Context
import de.htwk.musicmanager.data.modelclasses.Artist
import de.htwk.musicmanager.data.source.network.LastFMService
import de.htwk.musicmanager.data.source.network.UnwrapInterceptor
import okhttp3.OkHttpClient
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppRepository private constructor(c: Context): Repository {

    private lateinit var lastFMService: LastFMService

    init {

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(UnwrapInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://ws.audioscrobbler.com")
            .addConverterFactory(GsonConverterFactory.create())
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


    /**
     * The Singleton-Implementation is based on this sample:
     * https://github.com/googlesamples/android-architecture-components/blob/master/BasicRxJavaSampleKotlin
     * /app/src/main/java/com/example/android/observability/persistence/UsersDatabase.kt
     */
    companion object {

        @Volatile
        var instance: AppRepository? = null

        fun getInstance(context: Context): AppRepository =
                instance ?: synchronized(this)  {
                    instance ?: AppRepository(context).also { instance = it }
                }
    }
}