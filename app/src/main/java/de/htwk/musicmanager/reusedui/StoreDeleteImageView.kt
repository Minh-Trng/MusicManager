package de.htwk.musicmanager.reusedui

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.MutableLiveData

class StoreDeleteImageView(context: Context, attrs: AttributeSet) : ImageView(context, attrs) {

    private lateinit var storeDrawable: Drawable
    private lateinit var deleteDrawable: Drawable

    private var stored = MutableLiveData<Boolean>()

    init {
        ResourcesCompat.getDrawable(context.resources, android.R.drawable.ic_menu_save, context.theme)?.let {
            storeDrawable = it
        }
        ResourcesCompat.getDrawable(context.resources, android.R.drawable.ic_delete, context.theme)?.let {
            deleteDrawable = it
        }

        stored.observeForever {
            if(it){
                setImageDrawable(deleteDrawable)
            }else{
                setImageDrawable(storeDrawable)
            }
        }
    }

    fun changeStored(newStoredValue: Boolean){
        stored.value = newStoredValue
    }
}