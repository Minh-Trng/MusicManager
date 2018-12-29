package de.htwk.musicmanager.albumdetail

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.htwk.musicmanager.data.source.Repository
import de.htwk.musicmanager.data.source.database.entities.Album
import de.htwk.musicmanager.util.SingleLiveEvent

class AlbumDetailViewModel(
    private val repository: Repository
) : ViewModel() {

    val errorOnLoading = SingleLiveEvent<Any>()

    val isLoading = ObservableBoolean(false)
    val album = MutableLiveData<Album>()

    fun searchAlbum(id:String){
        isLoading.set(true)
        repository.searchAlbum(id) {
            if(it != null){
                album.postValue(it)
            }else{
                album.postValue(Album())

                errorOnLoading.call()
            }
            isLoading.set(false)
        }
    }
}