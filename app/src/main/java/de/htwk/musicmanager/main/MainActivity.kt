package de.htwk.musicmanager.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import de.htwk.musicmanager.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        AlbumRepository.getInstance(this).searchArtists("riha", object: Callback<List<Artist>>{
//            override fun onFailure(call: Call<List<Artist>>, t: Throwable) {
//                Toast.makeText(this@MainActivity, "Error on Loading", Toast.LENGTH_LONG).show()
//                t.printStackTrace()
//            }
//
//            override fun onResponse(call: Call<List<Artist>>, response: Response<List<Artist>>) {
//                response.body()?.forEach {
//                    Log.d("Main", it.toString())
//                }
//            }
//
//        })
    }
}
