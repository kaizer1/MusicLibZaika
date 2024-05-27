package ru.neogame.musiclib

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.neogame.musiclib.R.layout.activity_main
import ru.neogame.musiclib.R.layout.activity_splash_screen
import ru.neogame.musiclib.databinding.ActivityMainBinding
import ru.neogame.musiclib.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Задержка перед переходом на основное активити
        val splashScreenDuration = 3000L // 2 секунды

        binding.root.postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }, splashScreenDuration)
    }
}