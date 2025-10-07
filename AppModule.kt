package com.example.spotifyclone.di

import com.example.spotifyclone.data.repository.MusicRepository
import com.example.spotifyclone.utils.MediaPlayerManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    @Provides
    @Singleton
    fun provideMusicRepository(): MusicRepository {
        return MusicRepository()
    }
    
    @Provides
    @Singleton
    fun provideMediaPlayerManager(): MediaPlayerManager {
        return MediaPlayerManager()
    }
}
