package com.adrammedia.googleads

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.adrammedia.googleads.databinding.ActivityMainBinding
import com.google.android.gms.ads.MobileAds

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        AdsServices.loadBanner(binding.container, this)
        binding.apply {
            button.setOnClickListener {
            AdsServices.getReward(this@MainActivity)
            AdsServices.showRewardedAdd(this@MainActivity, binding.button2)
               }
            button2.setOnClickListener {
                AdsServices.loadInterstitialAd(this@MainActivity)
                AdsServices.showInterstitialAd(this@MainActivity)
            }
        }
    }
}