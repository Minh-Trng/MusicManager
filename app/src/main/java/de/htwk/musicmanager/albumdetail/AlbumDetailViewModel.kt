package de.htwk.musicmanager.albumdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.htwk.musicmanager.data.source.Repository
import de.htwk.musicmanager.data.source.database.entities.Album

class AlbumDetailViewModel(repository: Repository) : ViewModel() {

    val album = MutableLiveData<Album>()

    fun searchAlbum(id:String){
        //TODO: implement
    }
}