package com.example.spotifyclone.data.repository

import com.example.spotifyclone.data.model.Playlist
import com.example.spotifyclone.data.model.Song
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MusicRepository {
    
    // In a real app, this would fetch from a remote API or local database
    fun getPlaylists(): Flow<List<Playlist>> {
        val songs = listOf(
            Song("1", "Blinding Lights", "The Weeknd", "After Hours", 203, 
                "https://example.com/cover1.jpg", "https://example.com/audio1.mp3"),
            Song("2", "Save Your Tears", "The Weeknd", "After Hours", 215,
                "https://example.com/cover2.jpg", "https://example.com/audio2.mp3"),
            Song("3", "Levitating", "Dua Lipa", "Future Nostalgia", 203,
                "https://example.com/cover3.jpg", "https://example.com/audio3.mp3"),
            Song("4", "Don't Start Now", "Dua Lipa", "Future Nostalgia", 183,
                "https://example.com/cover4.jpg", "https://example.com/audio4.mp3")
        )
        
        val playlists = listOf(
            Playlist("1", "Today's Top Hits", "The biggest songs right now", 
                "https://example.com/playlist1.jpg", songs),
            Playlist("2", "Chill Vibes", "Relaxing tracks for your day", 
                "https://example.com/playlist2.jpg", songs.reversed())
        )
        
        return flowOf(playlists)
    }
    
    fun getSongById(id: String): Song? {
        // In a real app, this would fetch from a database
        return getPlaylists().first().first().songs.find { it.id == id }
    }
}
