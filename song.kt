package com.example.spotifyclone.data.model

data class Song(
    val id: String,
    val title: String,
    val artist: String,
    val album: String,
    val duration: Int, // in seconds
    val coverUrl: String,
    val audioUrl: String
)
