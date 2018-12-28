package de.htwk.musicmanager.storedalbums


import android.app.Activity
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import de.htwk.musicmanager.R
import de.htwk.musicmanager.databinding.FragmentStoredAlbumsBinding
import de.htwk.musicmanager.main.MainActivity
import de.htwk.musicmanager.reusedui.AlbumsRecyclerViewAdapter
import de.htwk.musicmanager.util.obtainViewModel
import kotlinx.android.synthetic.main.fragment_stored_albums.*

class StoredAlbumsFragment : Fragment() {

    private lateinit var viewModel: StoredAlbumsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {

        (activity as MainActivity).supportActionBar?.title = "Stored Albums"
        viewModel = (activity as MainActivity).obtainViewModel(StoredAlbumsViewModel::class.java)
        val binding = FragmentStoredAlbumsBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = AlbumsRecyclerViewAdapter(ArrayList(), {id ->
            val action = StoredAlbumsFragmentDirections.actionShowAlbumDetails(id)
            Navigation.findNavController(view).navigate(action)
        }){
            id -> viewModel.deleteAlbum(id){message ->
                activity?.runOnUiThread { Toast.makeText(context, message, Toast.LENGTH_SHORT).show() }
            }
        }

        rvStoredAlbums.adapter = adapter
        rvStoredAlbums.layoutManager = GridLayoutManager(context, 2)

        viewModel.storedAlbums.observe(this, Observer {
            adapter.changeItemList(it)
            adapter.changeStoredAlbums(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_app, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId){
            R.id.searchFragment ->{
                Navigation.findNavController(activity as Activity, R.id.nav_host).
                        navigate(R.id.action_storedAlbumsFragment_to_searchFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
