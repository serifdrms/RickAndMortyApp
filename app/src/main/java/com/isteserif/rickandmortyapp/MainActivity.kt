package com.isteserif.rickandmortyapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import com.isteserif.rickandmortyapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        // activity_main.xml için ViewBinding'i kur
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Geri kalan her şeyi (fragment'ları göstermeyi)
        // XML'deki 'NavHostFragment' otomatik olarak halledecek.
    }
}