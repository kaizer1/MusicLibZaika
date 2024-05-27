package ru.neogame.musiclib

import android.content.Context
import android.media.browse.MediaBrowser
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import ru.neogame.musiclib.models.SongModel

object MyExoplayer {

    private var exoPlayer : ExoPlayer? = null
    private var currentSong : SongModel? =null
    private var currentSongId : String? = null

    fun getCurrentSong() : SongModel?{
        return currentSong
    }

    fun getCurrentSongId() : String? {
        return currentSongId
    }

    fun getInstance() : ExoPlayer?{
        return exoPlayer
    }

    fun startPlaying(context : Context, song : SongModel, songid : String){
        if(exoPlayer==null)
            exoPlayer = ExoPlayer.Builder(context).build()

        if(currentSong!=song){
            //Its a new song so start playing
            currentSong = song
            currentSongId = songid

            currentSong?.url?.apply {
                val mediaItem = MediaItem.fromUri(this)
                exoPlayer?.setMediaItem(mediaItem)
                exoPlayer?.prepare()
                exoPlayer?.play()

            }
        }
    }


}
