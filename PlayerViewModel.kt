package com.example.spotifyclone.ui.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spotifyclone.data.model.Song
import com.example.spotifyclone.utils.MediaPlayerManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val mediaPlayerManager: MediaPlayerManager
) : ViewModel() {
    
    val isPlaying = mediaPlayerManager.isPlaying
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )
    
    val currentPosition = mediaPlayerManager.currentPosition
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )
    
    fun play() {
        mediaPlayerManager.play()
    }
    
    fun pause() {
        mediaPlayerManager.pause()
    }
    
    fun loadSong(song: Song, context: android.content.Context) {
        mediaPlayerManager.loadSong(song, context)
    }
    
    fun getCurrentPosition(): Int = mediaPlayerManager.getCurrentPosition()
    
    fun getDuration(): Int = mediaPlayerManager.getDuration()
    
    fun seekTo(position: Int) {
        mediaPlayerManager.seekTo(position)
    }
    
    override fun onCleared() {
        super.onCleared()
        mediaPlayerManager.release()
    }
}
