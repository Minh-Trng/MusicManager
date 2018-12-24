package de.htwk.musicmanager.main

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import de.htwk.musicmanager.R
import de.htwk.musicmanager.data.modelclasses.Artist
import de.htwk.musicmanager.data.source.AppRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppRepository.getInstance(this).searchArtists("riha", object: Callback<List<Artist>>{
            override fun onFailure(call: Call<List<Artist>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error on Loading", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }

            override fun onResponse(call: Call<List<Artist>>, response: Response<List<Artist>>) {
                response.body()?.forEach {
                    Log.d("Main", it.toString())
                }
            }

        })
    }
}
