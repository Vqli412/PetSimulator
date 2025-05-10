package com.example.finalproject

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View

class PethomeView(context: Context, private val width: Int, private val height: Int) : View(context) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val daytime: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.daytime)
    private val nighttime: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.nighttime)
    private var useNight = false

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // pick which Bitmap to draw
        val bg = if (useNight) nighttime else daytime

        // draw it stretched to fill the view bounds
        val srcRect = Rect(0, 0, bg.width, bg.height)
        val dstRect = Rect(0, 0, width, height)
        canvas.drawBitmap(bg, srcRect, dstRect, paint)

    }

    fun toggleDayNight() {
        useNight = !useNight
        invalidate()
    }
}