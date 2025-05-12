package com.example.finalproject
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import android.view.MotionEvent
import android.view.View

class PethomeView : View {
    private lateinit var paint : Paint
    private lateinit var daytime : Bitmap
    private lateinit var nighttime : Bitmap

    private lateinit var settings : Bitmap
    private val settingsRect = Rect()
    private var settingsClickListener: (() -> Unit)? = null

    private var width : Int = 0
    private var height : Int = 0
    private var useNight = false
    private var capybara: Bitmap? = null


    constructor(context: Context, width: Int, height: Int) : super(context)  {
        paint = Paint()
        daytime = BitmapFactory.decodeResource(resources, R.drawable.daytime)
        nighttime = BitmapFactory.decodeResource(resources, R.drawable.nighttime)
        settings = BitmapFactory.decodeResource(resources, R.drawable.settings)
        this.width = width
        this.height = height
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // pick which Bitmap to draw
        val bg = if (useNight) nighttime else daytime

        // draw background stretched to fill the view bounds
        val srcRect = Rect(0, 0, bg.width, bg.height)
        val dstRect = Rect(0, 0, width, height)
        canvas.drawBitmap(bg, srcRect, dstRect, paint)

        capybara?.let {
            val left = bg.width / 5
            val top = bg.height / 1.75
            canvas.drawBitmap(it, left.toFloat(), top.toFloat(), paint)
        }

    }

    fun toggleDayNight() { //use this method for toggling the switch
        useNight = !useNight
        invalidate()
    }

    fun setOnSettingsClickListener(listener: () -> Unit) {
        settingsClickListener = listener
    }
    fun setCapybaraImage(resId: Int) {
        val original = BitmapFactory.decodeResource(resources, resId)
        val targetWidth = (width * 0.9).toInt()
        val aspectRatio = original.height.toFloat() / original.width
        val targetHeight = (targetWidth * aspectRatio).toInt()

        capybara = Bitmap.createScaledBitmap(original, targetWidth, targetHeight, true)
        invalidate()
    }

}