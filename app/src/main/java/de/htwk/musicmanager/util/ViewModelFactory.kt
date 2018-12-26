package de.htwk.musicmanager.util

import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.htwk.musicmanager.albumoverview.AlbumOverviewViewModel
import de.htwk.musicmanager.data.source.AlbumRepository
import de.htwk.musicmanager.data.source.Repository
import de.htwk.musicmanager.search.SearchFragmentViewModel


/**
 * based on: https://github.com/googlesamples/android-architecture/tree/to do-mvvm-live-kotlin/
 */
class ViewModelFactory private constructor(
    private val repository: Repository
) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(SearchFragmentViewModel::class.java) ->
                    SearchFragmentViewModel(repository)
                isAssignableFrom(AlbumOverviewViewModel::class.java) ->
                    AlbumOverviewViewModel(repository)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application) =
            INSTANCE ?: synchronized(ViewModelFactory::class.java) {
                INSTANCE ?: ViewModelFactory(AlbumRepository.getInstance(application))
                    .also { INSTANCE = it }
            }


        @VisibleForTesting
        fun destroyInstance() {
            INSTANCE = null
        }
    }

}