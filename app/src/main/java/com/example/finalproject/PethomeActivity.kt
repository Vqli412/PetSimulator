package com.example.finalproject

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
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        buildViewByCode()
    }

    fun buildViewByCode() {
        var width: Int = resources.displayMetrics.widthPixels
        var height: Int = resources.displayMetrics.heightPixels
        var rectangle: Rect = Rect(0, 0, 0, 0)
        window.decorView.getWindowVisibleDisplayFrame(rectangle)
        var statusBar: Int = rectangle.top

        pethomeView = PethomeView(this, width, height - statusBar)

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