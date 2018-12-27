package de.htwk.musicmanager.albumoverview


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import de.htwk.musicmanager.databinding.FragmentAlbumOverviewBinding
import de.htwk.musicmanager.main.MainActivity
import de.htwk.musicmanager.reusedui.AlbumsRecyclerViewAdapter
import de.htwk.musicmanager.util.obtainViewModel
import kotlinx.android.synthetic.main.fragment_album_overview.*

class AlbumOverviewFragment : Fragment() {

    lateinit var viewModel: AlbumOverviewViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        (activity as MainActivity).supportActionBar?.title = "Top Albums"

        viewModel = (activity as MainActivity).obtainViewModel(AlbumOverviewViewModel::class.java)
        val binding = FragmentAlbumOverviewBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = AlbumsRecyclerViewAdapter(ArrayList()){
            id ->   val action = AlbumOverviewFragmentDirections.actionShowAlbumDetails(id)
                    Navigation.findNavController(view).navigate(action)
        }

        arguments?.let {
            viewModel.searchAlbums(AlbumOverviewFragmentArgs.fromBundle(it).artistName)
        }

        rvAlbumsOfArtist.adapter = adapter
        rvAlbumsOfArtist.layoutManager = GridLayoutManager(context,2)

        viewModel.errorOnLoading.observe(this, Observer {
            Toast.makeText(context, "Error on loading, check your connection", Toast.LENGTH_SHORT).show()
        })

        viewModel.albums.observe(this, Observer {
            adapter.changeItemList(it.filter { album -> album.name != "(null)"})
        })

        super.onViewCreated(view, savedInstanceState)
    }



}
