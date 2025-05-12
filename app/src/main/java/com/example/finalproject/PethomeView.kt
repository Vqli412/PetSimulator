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
    private var isDay = false

    constructor(context: Context, width: Int, height: Int, isDay : Boolean) : super(context)  {
        paint = Paint()
        daytime = BitmapFactory.decodeResource(resources, R.drawable.daytime)
        nighttime = BitmapFactory.decodeResource(resources, R.drawable.nighttime)
        settings = BitmapFactory.decodeResource(resources, R.drawable.settings)
        this.width = width
        this.height = height
        isClickable = true
        this.isDay = isDay
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // pick which Bitmap to draw
        val bg = if (isDay) daytime else nighttime

        // draw background stretched to fill the view bounds
        val srcRect = Rect(0, 0, bg.width, bg.height)
        val dstRect = Rect(0, 0, width, height)
        canvas.drawBitmap(bg, srcRect, dstRect, paint)

        //draw gacha capybara here, should be associated with firebase account

        //draw settings button
        val scale = 0.1f
        val buttonW = (width * scale).toInt()
        val buttonH = (settings.height * buttonW) / settings.width
        settingsRect.set(
            width - buttonW,
            0,
            width,
            buttonH)
        canvas.drawBitmap(settings, null, settingsRect, paint)
    }

    fun setOnSettingsClickListener(listener: () -> Unit) {
        settingsClickListener = listener
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            // if the user lifted their finger inside settingsRect
            if (settingsRect.contains(event.x.toInt(), event.y.toInt())) {
                settingsClickListener?.invoke()
                return true
            }
        }
        return super.onTouchEvent(event)
    }

}