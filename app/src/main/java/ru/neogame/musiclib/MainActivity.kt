package ru.neogame.musiclib

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SnapshotMetadata
import org.checkerframework.framework.qual.HasQualifierParameter
import ru.neogame.musiclib.adapter.HistoryAdapter
import ru.neogame.musiclib.adapter.SectionSongListAdapter
import ru.neogame.musiclib.databinding.ActivityMainBinding
import ru.neogame.musiclib.models.SongModel


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navigationView = binding.navigationView

        setSupportActionBar(binding.toolbar)

        val myDraw = binding.drawerLayout
        val toggle = ActionBarDrawerToggle(this, myDraw, binding.toolbar, R.string.navigation_draw_open, R.string.navigation_draw_clo)
        myDraw.addDrawerListener(toggle)
        toggle.syncState()



//        val dad = hashMapOf(
//            "date" to "20.04.2024",
//            "nameTitle" to "sound2"
//
//
//        )
//        db.collection("history")
//            .add(dad)
//            .addOnSuccessListener {
//                println(" ok load correct ! ")
//            }


        navigationView.setNavigationItemSelectedListener {


            when (it.itemId){
                R.id.nav_history -> {
                     println(" my history ")
                    setupHistory(binding.section1RecyclerView)
                    myDraw.closeDrawer(GravityCompat.START)
                    true
                }

                R.id.nav_mainmenu -> {
                    println(" my main menu ")
                    setupSection("secion_1",binding.section1RecyclerView)
                    myDraw.closeDrawer(GravityCompat.START)
                   true
                }

                else -> {
                    false
                }
            }

        }


        binding.swipeRefreshLayout.setOnRefreshListener {
            setupSection("secion_1",binding.section1RecyclerView)
            binding.swipeRefreshLayout.isRefreshing = false
        }


        setupSection("secion_1",binding.section1RecyclerView)

    }


    override fun onResume() {
        super.onResume()
        showPlayerView()
    }


    fun showPlayerView(){
        binding.playerView.setOnClickListener {
            startActivity(Intent(this,PlayerActivity::class.java))
        }
        MyExoplayer.getCurrentSong()?.let {
            binding.playerView.visibility = View.VISIBLE
            binding.songTitleTextView.text = "Сейчас играет : " + it.title
            Glide.with(binding.songCoverImageView).load(it.coverUrl)
                .apply(
                    RequestOptions().transform(RoundedCorners(32))
                ).into(binding.songCoverImageView)
        } ?: run{
            binding.playerView.visibility = View.GONE
        }
    }

    private fun setupHistory(recyclesV : RecyclerView){

         val hashFinal = ArrayList<HashMap<String, String>>()

           FirebaseFirestore.getInstance().collection("history")
               .get()
               .addOnSuccessListener {

                   // get my all history data
                   val df = it.documents
                   for( doc in df) {
                       val hash = HashMap<String, String>()
                       println(" my data = ${doc.get("date")}")
                       println(" my title = ${doc.get("nameTitle")}")
                       hash["d"] = doc.get("date").toString()
                       hash["n"] = doc.get("nameTitle").toString()
                       hashFinal.add(hash)
                   }

                   recyclesV.layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.VERTICAL,false)
                   recyclesV.adapter = HistoryAdapter(hashFinal)

               }

        hashFinal.clear()
    }


    //Sections
    fun setupSection(id : String,recyclerView: RecyclerView){
        FirebaseFirestore.getInstance().collection("sections")
            .document(id)
            .get().addOnSuccessListener {
                val section = it.toObject(SongModel::class.java)
                section?.apply {
                    recyclerView.layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.VERTICAL,false)
                    recyclerView.adapter = SectionSongListAdapter(songs)

                }
            }

    }
}





