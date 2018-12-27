package de.htwk.musicmanager.storedalbums


import android.app.Activity
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import de.htwk.musicmanager.R
import de.htwk.musicmanager.main.MainActivity

class StoredAlbumsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as MainActivity).supportActionBar?.title = "Stored Albums"

        return inflater.inflate(R.layout.fragment_stored_albums, container, false)
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
