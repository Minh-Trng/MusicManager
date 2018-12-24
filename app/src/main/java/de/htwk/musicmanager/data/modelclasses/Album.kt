package de.htwk.musicmanager.data.modelclasses

data class Album(
  val name : String,
  val artist : String,
  val imageUrl: String,
  val tracks : List<String>
)