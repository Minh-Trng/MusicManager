package de.htwk.musicmanager.storedalbums

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import de.htwk.musicmanager.data.source.Repository
import de.htwk.musicmanager.data.source.database.entities.Album

class StoredAlbumsViewModel(private val repository: Repository) : ViewModel() {

    val storedAlbums : LiveData<List<Album>> = repository.getStoredAlbums()
    val isEmpty = ObservableBoolean(false)

    init {
        storedAlbums.observeForever {
            isEmpty.set(it.isEmpty())
        }
    }

    fun deleteAlbum(id: String, messageCallback: (String) -> Unit) {
        repository.deleteAlbum(id, messageCallback)
    }
}