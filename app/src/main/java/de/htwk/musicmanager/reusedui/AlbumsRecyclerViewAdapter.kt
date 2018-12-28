package de.htwk.musicmanager.reusedui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import de.htwk.musicmanager.R
import de.htwk.musicmanager.data.source.database.entities.Album
import kotlinx.android.synthetic.main.item_album.view.*

class AlbumsRecyclerViewAdapter(
    private val items: ArrayList<Album>,
    private val albumClicked: (id: String) -> Unit,
    private val storeDeleteClicked: (id: String) -> Unit
) : RecyclerView.Adapter<AlbumsRecyclerViewAdapter.AlbumViewHolder>() {

    //used to determine whether an Album is in the database and set the storeDelete-Image accordingly
    private var storedAlbumNames : List<String> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_album, parent, false)

        return AlbumViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = items[position]

        holder.imgStoreDelete.changeStored(storedAlbumNames.contains(album.name))

        loadImage(album, holder.image)
        holder.albumName.text = album.name
        holder.artistName.text = album.artistName
        holder.parent.setOnClickListener { albumClicked(album.id) }
        holder.imgStoreDelete.setOnClickListener { storeDeleteClicked(album.id) }
    }

    fun changeItemList(newList: List<Album>){
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }

    fun changeStoredAlbums (storedAlbums : List<Album>){
        storedAlbumNames = storedAlbums.map { album -> album.name }
        notifyDataSetChanged()
    }

    private fun loadImage(album: Album, imageView: ImageView){
        val file = imageView.context.applicationContext.getFileStreamPath(album.id)
        if(file.exists()){
            Picasso.get().load(file).error(android.R.drawable.stat_notify_error).into(imageView)
        }else{
            val imageURL = if (album.imageURL.isEmpty()) "invalidPath" else album.imageURL
            Picasso.get().load(imageURL).error(android.R.drawable.stat_notify_error).into(imageView)
        }
    }

    class AlbumViewHolder(
        val parent: View,
        val image: ImageView = parent.imgAlbumCover,
        val imgStoreDelete: StoreDeleteImageView = parent.imgStoreDelete,
        val albumName: TextView = parent.txtAlbumName,
        val artistName: TextView = parent.txtArtistName
        ) : RecyclerView.ViewHolder(parent)

}