package de.htwk.musicmanager.search


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import de.htwk.musicmanager.data.modelclasses.Artist
import de.htwk.musicmanager.databinding.FragmentSearchBinding
import de.htwk.musicmanager.main.MainActivity
import de.htwk.musicmanager.util.obtainViewModel
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = (activity as MainActivity).obtainViewModel(SearchFragmentViewModel::class.java)
        val binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val dummyArtist = Artist("test", listOf(Artist.ImageInfo("", ""), Artist.ImageInfo("", "")))
        val adapter = ArtistSearchResultAdapter(context as Context, arrayListOf())
        listSearchArtistResults.adapter = adapter

        imgSearch.setOnClickListener {
            viewModel.searchArtists(editSearchArtist.text.toString())
        }

        viewModel.errorOnLoading.observe(this, Observer {
            Toast.makeText(context, "Error on loading, check your connection", Toast.LENGTH_LONG).show()
        })

        viewModel.searchResults.observe(this, Observer {
                newItems -> adapter.changeItemList(newItems)
        })

        super.onViewCreated(view, savedInstanceState)
    }

}
