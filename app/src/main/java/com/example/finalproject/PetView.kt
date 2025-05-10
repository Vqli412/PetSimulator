package com.example.finalproject
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import com.google.android.gms.ads.AdView
import androidx.core.view.setMargins

class PetView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val petImageView: ImageView
    private val adContainer: FrameLayout

    init {
        // Set up the root layout
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)

        // Background ImageView
        val background = ImageView(context).apply {
            setImageResource(R.drawable.temp_background)
            scaleType = ImageView.ScaleType.CENTER_CROP
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        }

        // Pet ImageView
        petImageView = ImageView(context).apply {
            setImageResource(R.drawable.dog)
            scaleType = ImageView.ScaleType.FIT_CENTER
            layoutParams = LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.CENTER
            }
        }

        // Ad container at bottom
        adContainer = FrameLayout(context).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.BOTTOM
                setMargins(0, 0, 0, 16)
            }
        }

        // Add all views to the root
        addView(background)
        addView(petImageView)
        addView(adContainer)
    }

    fun setPetImage(resId: Int) {
        petImageView.setImageResource(resId)
    }

    fun setAdView(adView: AdView) {
        adContainer.removeAllViews()
        adContainer.addView(adView)
    }
}