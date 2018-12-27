package de.htwk.musicmanager.albumdetail

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.htwk.musicmanager.data.source.Repository
import de.htwk.musicmanager.data.source.database.entities.Album

class AlbumDetailViewModel(
    private val repository: Repository
) : ViewModel() {

    /**
     *  has been turned into Type Boolean in order to work with the workaround in [AlbumDetailFragment]
     */
    val errorOnLoading = MutableLiveData<Boolean>().apply { value = false }

    val isLoading = ObservableBoolean(false)
    val album = MutableLiveData<Album>()

    fun searchAlbum(id:String){
        isLoading.set(true)
        repository.searchAlbum(id) {
            if(it != null){
                album.value = it
            }else{
                album.value = Album()

                /**
                 * I am aware that the following is not recommended according to this article:
                 * https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150
                 */
                errorOnLoading.value = true
                errorOnLoading.value = false
            }
            isLoading.set(false)
        }
    }
}