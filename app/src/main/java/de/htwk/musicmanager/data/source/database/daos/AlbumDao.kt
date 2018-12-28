package de.htwk.musicmanager.data.source.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.htwk.musicmanager.data.source.database.entities.Album

@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlbum(vararg album: Album)

    @Query("SELECT * FROM album")
    fun getAllAlbums() : LiveData<List<Album>>

    @Query("SELECT * FROM album WHERE id = :id")
    fun getAlbumByID(id: String) : Album?

    @Query("DELETE FROM Album WHERE id = :id")
    fun deleteAlbumByID(id: String) : Int
}