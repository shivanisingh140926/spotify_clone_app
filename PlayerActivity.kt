package com.example.spotifyclone.ui.player

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.SeekBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.spotifyclone.R
import com.example.spotifyclone.data.model.Song
import com.example.spotifyclone.utils.MediaPlayerManager
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_player.*

@AndroidEntryPoint
class PlayerActivity : AppCompatActivity() {
    
    private val viewModel: PlayerViewModel by viewModels()
    private lateinit var mediaPlayerManager: MediaPlayerManager
    private lateinit var currentSong: Song
    private val handler = Handler(Looper.getMainLooper())
    private val updateSeekBarRunnable = object : Runnable {
        override fun run() {
            seekBar.progress = viewModel.getCurrentPosition()
            handler.postDelayed(this, 1000)
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        
        mediaPlayerManager = MediaPlayerManager()
        mediaPlayerManager.initializeMediaPlayer(this)
        
        currentSong = intent.getParcelableExtra("song") ?: return
        
        setupViews()
        setupObservers()
        loadSong()
    }
    
    private fun setupViews() {
        // Load song cover
        Picasso.get()
            .load(currentSong.coverUrl)
            .placeholder(R.drawable.ic_music_note)
            .into(ivCover)
            
        tvTitle.text = currentSong.title
        tvArtist.text = currentSong.artist
        
        // Setup play/pause button
        btnPlayPause.setOnClickListener {
            if (viewModel.isPlaying.value == true) {
                viewModel.pause()
            } else {
                viewModel.play()
            }
        }
        
        // Setup seek bar
        seekBar.max = currentSong.duration * 1000
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    viewModel.seekTo(progress)
                }
            }
            
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        
        // Setup skip button (in a real app, this would skip to next song)
        btnSkip.setOnClickListener {
            // Skip logic would go here
        }
    }
    
    private fun setupObservers() {
        viewModel.isPlaying.observe(this) { isPlaying ->
            btnPlayPause.setImageResource(
                if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play
            )
        }
    }
    
    private fun loadSong() {
        viewModel.loadSong(currentSong, this)
        handler.post(updateSeekBarRunnable)
    }
    
    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(updateSeekBarRunnable)
        mediaPlayerManager.release()
    }
}
