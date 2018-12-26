package de.htwk.musicmanager.data.source.network

import de.htwk.musicmanager.data.modelclasses.Artist
import de.htwk.musicmanager.data.source.database.entities.Album
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface LastFMService {

    //TODO:put params directly into annotations

    @GET("/2.0/")
    fun searchArtists(@QueryMap options :  Map<String, String>): Call<List<Artist>>

    @GET("/2.0/")
    fun searchAlbums(@QueryMap options :  Map<String, String>): Call<List<Album>>

    @GET("/2.0/")
    fun getAlbumDetails(@QueryMap options: Map<String, String>): Call<Album>

    companion object {
        const val API_KEY = "ef2a73ec374af7cc2b9ee96ffb9f9bf6"
    }
}