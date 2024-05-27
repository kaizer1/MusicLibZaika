package ru.neogame.musiclib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.firestore.FirebaseFirestore
import ru.neogame.musiclib.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity() {

    lateinit var binding: ActivityPlayerBinding
    lateinit var exoPlayer: ExoPlayer

    var playerListener = object : Player.Listener{
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            super.onIsPlayingChanged(isPlaying)
            showGif(isPlaying)
        }

        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            super.onMediaItemTransition(mediaItem, reason)
          println(" media transitions ")
        }

        override fun onEvents(player: Player, events: Player.Events) {
            super.onEvents(player, events)

            println(" in press event ! ")
        }

    }

    @OptIn(UnstableApi::class) override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MyExoplayer.getCurrentSong()?.apply {
            binding.songTitleTextView.text = title
            binding.songSubtitleTextView.text = subtitle
            Glide.with(binding.songCoverImageView).load(coverUrl)
                .circleCrop()
                .into(binding.songCoverImageView)
            Glide.with(binding.songGifImageView).load(R.drawable.media_playing)
                .circleCrop()
                .into(binding.songGifImageView)
            exoPlayer = MyExoplayer.getInstance()!!
            binding.playerView.player = exoPlayer
            binding.playerView.showController()
            binding.playerView.setShowNextButton(true)
            //binding.playerView.setShowSubtitleButton(true)
            exoPlayer.addListener(playerListener)

            val mediaSession = MediaSession.Builder(baseContext, exoPlayer!!).build()

            binding.saveButton.setOnClickListener {

               FirebaseFirestore.getInstance().collection("songs")
                .document(MyExoplayer.getCurrentSongId()!!).get()
                .addOnSuccessListener { it ->




                }

            }


            binding.iconArrowLeft.setOnClickListener {
                super.onBackPressed()
            }


        }
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer?.removeListener(playerListener)
    }



    fun showGif(show : Boolean){
        if(show)
            binding.songGifImageView.visibility = View.VISIBLE
        else
            binding.songGifImageView.visibility = View.INVISIBLE
    }
}