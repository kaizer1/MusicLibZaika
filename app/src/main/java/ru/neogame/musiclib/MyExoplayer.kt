package ru.neogame.musiclib




import android.content.Context
import android.media.browse.MediaBrowser
import android.provider.MediaStore.Audio.Media
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ShuffleOrder
import ru.neogame.musiclib.models.SongModel



@OptIn(UnstableApi::class)
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

    fun addMediaItem(ia : Int , context : Context, song : SongModel){
            if(exoPlayer==null)
            exoPlayer = ExoPlayer.Builder(context).build()

        println(" number == $ia ")
        println(" url == ${song.url}")
        exoPlayer?.addMediaItem(ia - 1, MediaItem.fromUri(song.url))

        exoPlayer?.playlistMetadata


    }

    fun setCurrentSong(song1 :String){
        currentSongId = song1
    }

    fun startPlaying(context : Context, song : SongModel, songid : String){
        if(exoPlayer==null)
            exoPlayer = ExoPlayer.Builder(context).build()

        if(currentSong!=song){
            //Its a new song so start playing
            currentSong = song
            currentSongId = songid

            val tedf = songid.substring(5).toInt()
            println(" current MNN $tedf")
            when(tedf){

                1 -> { // all ok

                }



                2 -> { // all ok
                    exoPlayer!!.seekToNextMediaItem()
                }

                3 -> { // all ok

                     exoPlayer!!.seekToNextMediaItem()
                     exoPlayer!!.seekToNextMediaItem()

                }

                4 -> { // number five &??? ?

                     exoPlayer!!.seekToNextMediaItem()
                     exoPlayer!!.seekToNextMediaItem()
                     exoPlayer!!.seekToNextMediaItem()
                }

                5 -> {  // all ok

                     exoPlayer!!.seekToNextMediaItem()
                     exoPlayer!!.seekToNextMediaItem()
                     exoPlayer!!.seekToNextMediaItem()
                     exoPlayer!!.seekToNextMediaItem()
                }

                6 -> {  // all ok !

                     exoPlayer!!.seekToNextMediaItem()
                     exoPlayer!!.seekToNextMediaItem()
                     exoPlayer!!.seekToNextMediaItem()
                     exoPlayer!!.seekToNextMediaItem()
                     exoPlayer!!.seekToNextMediaItem()
                }


            }

            exoPlayer?.prepare()
                exoPlayer?.play()

          // exoPlayer?.shuffleModeEnabled = true

//            currentSong?.url?.apply {
//                //val mediaItem = MediaItem.fromUri(this)
//                //exoPlayer?.setMediaItem(mediaItem)
//                exoPlayer?.prepare()
//                exoPlayer?.play()
//
//            }
        }
    }


}
