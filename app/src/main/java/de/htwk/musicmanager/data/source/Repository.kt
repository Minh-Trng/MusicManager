package de.htwk.musicmanager.data.source

import de.htwk.musicmanager.data.modelclasses.Artist
import retrofit2.Callback

interface Repository {

    fun searchArtists(name: String, callback: Callback<List<Artist>>)

}