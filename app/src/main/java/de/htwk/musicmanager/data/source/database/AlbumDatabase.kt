package de.htwk.musicmanager.data.source.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import de.htwk.musicmanager.data.source.database.daos.AlbumDao
import de.htwk.musicmanager.data.source.database.entities.Album

@Database(entities = [Album::class], version = 1)
@TypeConverters(Album.TracksConverter::class)
abstract class AlbumDatabase : RoomDatabase(){
    abstract fun albumDao() : AlbumDao

    companion object {

        const val DATABASENAME = "album_database"

        private var INSTANCE: AlbumDatabase? = null

        private val lock = Any()

        fun getInstance(context: Context): AlbumDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AlbumDatabase::class.java, DATABASENAME
                    )
                        .allowMainThreadQueries() //todo: später entfernen
                        .build()
                }
                return INSTANCE!!
            }
        }
    }
}