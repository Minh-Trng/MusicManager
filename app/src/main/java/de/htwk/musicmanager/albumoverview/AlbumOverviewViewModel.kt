package de.htwk.musicmanager.albumoverview

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.htwk.musicmanager.data.source.Repository
import de.htwk.musicmanager.data.source.database.entities.Album
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlbumOverviewViewModel(private val repository: Repository) : ViewModel() {

    val errorOnLoading = MutableLiveData<Any>()
    val isLoading = ObservableBoolean(false)
    val albums = MutableLiveData<List<Album>>()

    fun searchAlbums(artistName: String){

        isLoading.set(true)

        repository.searchAlbums(artistName, object: Callback<List<Album>>{
            override fun onFailure(call: Call<List<Album>>, t: Throwable) {
                isLoading.set(false)
                errorOnLoading.value = Any()
                t.printStackTrace()
            }

            override fun onResponse(call: Call<List<Album>>, response: Response<List<Album>>) {
                isLoading.set(false)
                if(response.body() == null){
                    errorOnLoading.value = Any()
                }else{
                    albums.value = response.body()
                }
            }
        })
    }
}