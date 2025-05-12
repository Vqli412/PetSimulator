package com.example.finalproject

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import androidx.core.content.ContextCompat


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
        val width = resources.displayMetrics.widthPixels
        val height = resources.displayMetrics.heightPixels
        val rectangle = Rect(0, 0, 0, 0)
        window.decorView.getWindowVisibleDisplayFrame(rectangle)
        val statusBar = rectangle.top

        pethomeView = PethomeView(this, width, height - statusBar)

        val capyResId = intent.getIntExtra("capyResId", 0)
        if (capyResId != 0) {
            pethomeView.setCapybaraImage(capyResId)
        }

        adView = AdView(this).apply {
            setAdSize(AdSize.BANNER)
            adUnitId = "ca-app-pub-3940256099942544/6300978111"
            loadAd(AdRequest.Builder().build())
        }

        val barHeight = 80

        val happinessBar = android.widget.ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal).apply {
            max = 1000
            progress = CapyActivity.happiness
            layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, barHeight).apply {
                topMargin = 50 // spacing from top of screen
                leftMargin = 40
                rightMargin = 40
                gravity = android.view.Gravity.TOP
            }
            progressDrawable = ContextCompat.getDrawable(context, R.drawable.happiness_progress)
            background = null
            translationZ = 5f
        }

        val happinessText = android.widget.TextView(this).apply {
            text = "Happiness ${CapyActivity.happiness}/1000"
            textSize = 25f
            setTextColor(android.graphics.Color.WHITE)
            setShadowLayer(4f, 2f, 2f, android.graphics.Color.BLACK)
            layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
                topMargin = 50 + barHeight + 8 // position under the bar
                gravity = android.view.Gravity.TOP
            }
            textAlignment = android.view.View.TEXT_ALIGNMENT_CENTER
        }
        pethomeView.setOnCapybaraTouchedListener {
            CapyActivity.happiness = (CapyActivity.happiness + 1).coerceAtMost(1000)
            happinessBar.progress = CapyActivity.happiness
            happinessText.text = "Happiness ${CapyActivity.happiness}/1000"
        }

        val overlayLayout = FrameLayout(this).apply {
            layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            addView(pethomeView)
            addView(happinessBar)
            addView(happinessText)
        }

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            addView(overlayLayout, LinearLayout.LayoutParams(MATCH_PARENT, 0, 1f))
            addView(adView, LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT))
        }

        setContentView(root)

        // Timer to decrease happiness
        val handler = android.os.Handler()
        val runnable = object : Runnable {
            override fun run() {
                if (CapyActivity.happiness > 0) {
                    CapyActivity.happiness -= 5
                    happinessBar.progress = CapyActivity.happiness
                    happinessText.text = "Happiness ${CapyActivity.happiness}/1000"
                    handler.postDelayed(this, 500)
                }
            }
        }
        handler.post(runnable)
    }



}