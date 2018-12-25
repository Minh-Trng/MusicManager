package de.htwk.musicmanager.storedalbums

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import de.htwk.musicmanager.data.source.Repository
import de.htwk.musicmanager.data.source.database.entities.Album

class StoredAlbumsViewModel(private val repository: Repository) : ViewModel() {

    val storedAlbums : LiveData<List<Album>> = repository.getStoredAlbums()
}