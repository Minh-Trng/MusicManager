package de.htwk.musicmanager.albumdetail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import de.htwk.musicmanager.R
import de.htwk.musicmanager.main.MainActivity
import de.htwk.musicmanager.util.obtainViewModel

class AlbumDetailFragment : Fragment() {

    private lateinit var viewModel: AlbumDetailViewModel

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        viewModel = (activity as MainActivity).obtainViewModel(AlbumDetailViewModel::class.java)
        //TODO: Binding
        return inflater.inflate(R.layout.fragment_album_detail, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.album.observe(this, Observer {
            //TODO load image from files. falls nicht vorhanden, mit ImageURL (aber nullcheck, weil diese nicht persistent ist)
        })
    }


}
