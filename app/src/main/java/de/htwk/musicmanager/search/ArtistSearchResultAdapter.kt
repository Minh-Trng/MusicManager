package de.htwk.musicmanager.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.squareup.picasso.Picasso
import de.htwk.musicmanager.R
import de.htwk.musicmanager.data.modelclasses.Artist
import kotlinx.android.synthetic.main.item_search_result.view.*

class ArtistSearchResultAdapter(
    c: Context,
    private var items: ArrayList<Artist>,
    private val callback: (name: String) -> Unit)
    : ArrayAdapter<Artist>(c, R.layout.item_search_result, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val artist = items[position]

        var resultView = convertView
        if(resultView == null){
            resultView = LayoutInflater.from(context).inflate(R.layout.item_search_result, parent, false)
        }

        val imageURL = if (artist.imageURL.isEmpty()) "invalidPath" else artist.imageURL
        Picasso.get().load(imageURL).error(android.R.drawable.stat_notify_error).into(resultView!!.imgSearchResultEntry)
        resultView.txtSearchResultEntry.text = artist.name

        resultView.setOnClickListener { callback(artist.name)}

        return resultView
    }

    fun changeItemList(newItems : List<Artist>){
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}