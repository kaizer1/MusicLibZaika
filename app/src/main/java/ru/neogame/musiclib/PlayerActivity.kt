package ru.neogame.musiclib

import android.content.Intent
import android.media.session.PlaybackState
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Tracks
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.memoryCacheSettings
import com.google.firebase.firestore.persistentCacheSettings
import ru.neogame.musiclib.databinding.ActivityPlayerBinding
import ru.neogame.musiclib.models.SongModel

class PlayerActivity : AppCompatActivity() {

    lateinit var binding: ActivityPlayerBinding
    lateinit var exoPlayer: ExoPlayer

    var playerListener = @UnstableApi object : Player.Listener{

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            super.onIsPlayingChanged(isPlaying)
            showGif(isPlaying)
        }




        override fun onTracksChanged(tracks: Tracks) {


           println(" my current ID  = ${ exoPlayer.currentMediaItemIndex } " )

               //binding.songTitleTextView.text = ""
            //binding.songSubtitleTextView.text = ""
            //binding.songCoverImageView =


               FirebaseFirestore.getInstance().collection("songs")
                .document("song_"+(exoPlayer.currentMediaItemIndex.toInt() + 1)).get()
                .addOnSuccessListener {
                  val song = it.toObject(SongModel::class.java)

                    song?.apply {
                        binding.songTitleTextView.text = title
                        binding.songSubtitleTextView.text = subtitle

                        println(" my title ${title}")

                        Glide.with(binding.songCoverImageView).load(coverUrl)
                            .apply(
                                RequestOptions().transform(RoundedCorners(32))
                            )
                            .into(binding.songCoverImageView)
                        MyExoplayer.setCurrentSong("song_"+exoPlayer.currentMediaItemIndex)
//                        binding.root.setOnClickListener {
//                            MyExoplayer.startPlaying(binding.root.context,song, songId)
//                            it.context.startActivity(Intent(it.context,PlayerActivity::class.java))
//                        }
                    }
                }



               MyExoplayer.getCurrentSong()?.apply {
                   binding.songTitleTextView.text = title
                   binding.songSubtitleTextView.text = subtitle
                   Glide.with(binding.songCoverImageView).load(coverUrl)
                       .circleCrop()
                       .into(binding.songCoverImageView)
                   Glide.with(binding.songGifImageView).load(R.drawable.media_playing)
                       .circleCrop()
                       .into(binding.songGifImageView)
               }
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

            if(exoPlayer.hasNextMediaItem()){
                println(" this next item's ! ")
            }

//            val mediaSession = MediaSession.Builder(baseContext, exoPlayer!!).build()
//            mediaSession.player = exoPlayer


            binding.saveButton.setOnClickListener {

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