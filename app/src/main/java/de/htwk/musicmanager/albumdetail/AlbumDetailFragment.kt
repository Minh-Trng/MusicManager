package de.htwk.musicmanager.albumdetail


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import de.htwk.musicmanager.databinding.FragmentAlbumDetailBinding
import de.htwk.musicmanager.main.MainActivity
import de.htwk.musicmanager.util.obtainViewModel
import kotlinx.android.synthetic.main.fragment_album_detail.*

class AlbumDetailFragment : Fragment() {

    private lateinit var viewModel: AlbumDetailViewModel
    private lateinit var binding : FragmentAlbumDetailBinding

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        (activity as MainActivity).supportActionBar?.title = "Album Details"

        viewModel = (activity as MainActivity).obtainViewModel(AlbumDetailViewModel::class.java)
        binding = FragmentAlbumDetailBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TracksAdapter(context as Context, ArrayList()){
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it)))
        }

        listTracks.adapter = adapter

        viewModel.album.observe(this, Observer {
            adapter.changeItems(it.tracks)

            /**
             * workaround, because the fields in [de.htwk.musicmanager.data.source.database.entities.Album]
             * are not observable. The clean solution (probably) would have been to let Album-Class implement
             * [androidx.databinding.BaseObservable] and use @get:Bindable-Annotations. There were multiple approaches
             * that I could think of, but they either did not work or were more complicated, so I decided to
             * stick with this solution
             * Edit: Not using Databinding at all might have been the best solution, since the backing data
             * of this fragment does not change in the background or through user Interactions
             */
            binding.viewModel = viewModel
        })

        viewModel.errorOnLoading.observe(this, Observer {
            Toast.makeText(context, "No Data for the selected album found", Toast.LENGTH_SHORT).show()
        })

        arguments?.let {
            viewModel.searchAlbum(AlbumDetailFragmentArgs.fromBundle(it).albumID)
        }
    }

}
