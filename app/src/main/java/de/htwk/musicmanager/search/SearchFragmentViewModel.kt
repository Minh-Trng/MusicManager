package de.htwk.musicmanager.search

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.htwk.musicmanager.data.modelclasses.Artist
import de.htwk.musicmanager.data.source.Repository
import de.htwk.musicmanager.util.SingleLiveEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragmentViewModel(private val repository: Repository) : ViewModel() {

    val errorOnLoading = SingleLiveEvent<Any>()

    var searchResults = MutableLiveData<List<Artist>>()
    var isLoading = ObservableBoolean(false)

    fun searchArtists(name: String){
        isLoading.set(true)

        repository.searchArtists(name, object: Callback<List<Artist>>{
            override fun onFailure(call: Call<List<Artist>>, t: Throwable) {
                isLoading.set(false)
                errorOnLoading.call()
                t.printStackTrace()
            }

            override fun onResponse(call: Call<List<Artist>>, response: Response<List<Artist>>) {
                isLoading.set(false)
                if(response.body() == null){
                    errorOnLoading.call()
                }else{
                    searchResults.value = response.body()
                }

            }
        })
    }
}