package com.example.spotifyclone.utils

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.spotifyclone.data.model.Song
import java.io.IOException

class MediaPlayerManager {
    private var mediaPlayer: MediaPlayer? = null
    private var currentSong: Song? = null
    
    private val _isPlaying = MutableLiveData<Boolean>()
    val isPlaying: LiveData<Boolean> = _isPlaying
    
    private val _currentPosition = MutableLiveData<Int>()
    val currentPosition: LiveData<Int> = _currentPosition
    
    fun initializeMediaPlayer(context: Context) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer().apply {
                setOnPreparedListener { 
                    _isPlaying.value = false
                }
                setOnCompletionListener {
                    _isPlaying.value = false
                }
            }
        }
    }
    
    fun loadSong(song: Song, context: Context) {
        if (currentSong?.id == song.id) return
        
        currentSong = song
        mediaPlayer?.reset()
        
        try {
            mediaPlayer?.setDataSource(context, Uri.parse(song.audioUrl))
            mediaPlayer?.prepareAsync()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    
    fun play() {
        if (mediaPlayer?.isPlaying == false) {
            mediaPlayer?.start()
            _isPlaying.value = true
        }
    }
    
    fun pause() {
        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.pause()
            _isPlaying.value = false
        }
    }
    
    fun getCurrentPosition(): Int {
        return mediaPlayer?.currentPosition ?: 0
    }
    
    fun getDuration(): Int {
        return mediaPlayer?.duration ?: 0
    }
    
    fun seekTo(position: Int) {
        mediaPlayer?.seekTo(position)
    }
    
    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
        currentSong = null
        _isPlaying.value = false
    }
}
