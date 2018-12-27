package de.htwk.musicmanager.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import de.htwk.musicmanager.data.source.database.entities.Album

class BindingAdapters {
    companion object {
        @JvmStatic
        @BindingAdapter("album")
        fun setImage(view: ImageView, album: Album?){
            album?.let {
                val file = view.context.applicationContext.getFileStreamPath(album.id)
                if(file.exists()){
                    Picasso.get().load(file).error(android.R.drawable.stat_notify_error).into(view)
                }else {
                    var url = album.imageURL
                    if(url.isEmpty()){
                        url = "invalidPath"
                    }

                    Picasso.get().load(url).error(android.R.drawable.stat_notify_error).into(view)

                }
            }

        }
    }

    private fun fileExists(context: Context, fname: String): Boolean {
        val file = context.applicationContext.getFileStreamPath(fname)
        return file.exists()
    }
}