package de.htwk.musicmanager.data.source.database.entities

class Album(
    val name : String,
    val artist : String,
    val imageUrl: String,
    val tracks : List<String>
)