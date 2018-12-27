package de.htwk.musicmanager.data.source

import androidx.lifecycle.LiveData
import de.htwk.musicmanager.data.modelclasses.Artist
import de.htwk.musicmanager.data.source.database.entities.Album
import retrofit2.Callback

interface Repository {

    fun searchArtists(name: String, callback: Callback<List<Artist>>)

    fun searchAlbums(artistName: String, callback: Callback<List<Album>>)

    fun searchAlbum(albumID: String, update: (album: Album?) -> Unit)

    fun getStoredAlbums() : LiveData<List<Album>>

}