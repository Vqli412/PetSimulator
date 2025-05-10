package com.example.finalproject
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdSize.AUTO_HEIGHT
import com.google.android.gms.ads.AdSize.FULL_WIDTH
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

class PetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Mobile Ads SDK
        MobileAds.initialize(this) {}

        // Create the AdView
        val adView = AdView(this).apply {
            adUnitId = "ca-app-pub-3940256099942544/6300978111" // Test ad unit ID
        }
        adView.setAdSize(AdSize(FULL_WIDTH, AUTO_HEIGHT))

        // Create the PetView
        val petView = PetView(this).apply {
            setPetImage(R.drawable.dog)
            setAdView(adView)
        }

        setContentView(petView)

        // Load the ad
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

}