package com.example.finalproject

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

class PethomeActivity : AppCompatActivity() {
    private lateinit var pethomeView : PethomeView
    private lateinit var adView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val capyResId = intent.getIntExtra("capyResId", 0)

        val petView = PethomeView(this, resources.displayMetrics.widthPixels, resources.displayMetrics.heightPixels)

        if (capyResId != 0) {
            petView.setCapybaraImage(capyResId)
        }

        setContentView(petView)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            buildViewByCode()
        }
    }

    fun buildViewByCode() {
        var width: Int = resources.displayMetrics.widthPixels
        var height: Int = resources.displayMetrics.heightPixels
        var rectangle: Rect = Rect(0, 0, 0, 0)
        window.decorView.getWindowVisibleDisplayFrame(rectangle)
        var statusBar: Int = rectangle.top

        val prefs = getSharedPreferences(SettingsActivity.PREFS, Context.MODE_PRIVATE)
        pethomeView = PethomeView(this, width, height - statusBar)

        val capyResId = intent.getIntExtra("capyResId", 0)
        if (capyResId != 0) {
            pethomeView.setCapybaraImage(capyResId)
        }

        //create and configure adview
        adView = AdView(this).apply {
            setAdSize(AdSize.BANNER)
            adUnitId = "ca-app-pub-3940256099942544/6300978111"
            loadAd(AdRequest.Builder().build())
        }

        // building linear layout to add both views
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)

            //pethomeview
            addView(pethomeView, LinearLayout.LayoutParams(MATCH_PARENT, 0, 1f))

            //banner
            addView(adView, LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT))
        }
        setContentView(root)
    }
}