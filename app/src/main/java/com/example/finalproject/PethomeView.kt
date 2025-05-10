package com.example.finalproject
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View

class PethomeView : View {
    private lateinit var paint : Paint
    private lateinit var daytime : Bitmap
    private lateinit var nighttime : Bitmap
    private var width : Int = 0
    private var height : Int = 0
    private var useNight = false

    constructor(context: Context, width: Int, height: Int) : super(context)  {
        paint = Paint()
        daytime = BitmapFactory.decodeResource(resources, R.drawable.daytime)
        nighttime = BitmapFactory.decodeResource(resources, R.drawable.nighttime)
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

        //draw gacha capybara here, should be associated with firebase account

    }

    fun toggleDayNight() { //use this method for toggling the switch
        useNight = !useNight
        invalidate()
    }
}