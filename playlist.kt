package com.example.spotifyclone.data.model

data class Playlist(
    val id: String,
    val name: String,
    val description: String,
    val coverUrl: String,
    val songs: List<Song>
)
