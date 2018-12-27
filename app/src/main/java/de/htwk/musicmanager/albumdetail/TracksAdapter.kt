package de.htwk.musicmanager.albumdetail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import de.htwk.musicmanager.R
import de.htwk.musicmanager.data.modelclasses.Track
import kotlinx.android.synthetic.main.item_track.view.*

class TracksAdapter(
    c: Context,
    private val items: ArrayList<Track>,
    private val callback: (url: String) -> Unit
) : ArrayAdapter<Track>(c, R.layout.item_track, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val track = items[position]

        var resultView = convertView
        if(resultView == null){
            resultView = LayoutInflater.from(context).inflate(R.layout.item_track, parent, false)
        }

        resultView!!.txtTrackTitle.text = track.name
        resultView.txtDuration.text = "${track.duration/60}:${(track.duration%60).toString().padStart(2, '0')}"

        resultView.setOnClickListener { callback(track.url) }

        return resultView
    }

    fun changeItems(newItems: List<Track>){
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}