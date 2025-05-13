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
    private lateinit var paint: Paint
    private lateinit var daytime: Bitmap
    private lateinit var nighttime: Bitmap

    private var width: Int = 0
    private var height: Int = 0
    private var isDay = true

    private var capybara: Bitmap? = null
    private var normalCapybara: Bitmap? = null
    private var pettingCapybara: Bitmap? = null
    private var capybaraRect: Rect? = null

    private var pethomeModel: PethomeModel


    constructor(context: Context, width: Int, height: Int) : super(context) {
        paint = Paint()
        daytime = BitmapFactory.decodeResource(resources, R.drawable.daytime)
        nighttime = BitmapFactory.decodeResource(resources, R.drawable.nighttime)
        this.width = width
        this.height = height
        val prefs = context.getSharedPreferences(SettingsActivity.PREFS, Context.MODE_PRIVATE)
        isDay = prefs.getBoolean(SettingsActivity.KEY_THEME_IS_DAY, true)

        pethomeModel = PethomeModel()
    }

    fun updateTheme() {
        daytime = BitmapFactory.decodeResource(resources, R.drawable.daytime)
        nighttime = BitmapFactory.decodeResource(resources, R.drawable.nighttime)
        val prefs = context.getSharedPreferences(SettingsActivity.PREFS, Context.MODE_PRIVATE)
        isDay = prefs.getBoolean(SettingsActivity.KEY_THEME_IS_DAY, true)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw background
        val bg = if (isDay) daytime else nighttime
        val srcRect = Rect(0, 0, bg.width, bg.height)
        val dstRect = Rect(0, 0, width, height)
        canvas.drawBitmap(bg, srcRect, dstRect, paint)

        // Draw capybara (normal or petting)
        val toDraw = if (pethomeModel.isPetting() && pettingCapybara != null) pettingCapybara else normalCapybara
        toDraw?.let {
            val left = (width - it.width) / 2
            val top = (height - it.height) / 2 + 100
            canvas.drawBitmap(it, left.toFloat(), top.toFloat(), paint)
            capybaraRect = Rect(left, top, left + it.width, top + it.height)
        }

    }

    fun setCapybaraImage(resId: Int) {
        val original = BitmapFactory.decodeResource(resources, resId)
        val targetWidth = (width * 0.9).toInt()
        val aspectRatio = original.height.toFloat() / original.width
        val targetHeight = (targetWidth * aspectRatio).toInt()

        val defaultBitmap = Bitmap.createScaledBitmap(original, targetWidth, targetHeight, true)

        val sleepingResId = when (resId) {
            R.drawable.boba_capy_default -> R.drawable.boba_capy_sleeping
            R.drawable.clover_capy_default -> R.drawable.clover_capy_sleeping
            R.drawable.orange_capy_default -> R.drawable.orange_capy_sleeping
            else -> resId
        }

        val pettingResId = when (resId) {
            R.drawable.boba_capy_default -> R.drawable.boba_capy_petting
            R.drawable.clover_capy_default -> R.drawable.clover_capy_petting
            R.drawable.orange_capy_default -> R.drawable.orange_capy_petting
            else -> resId
        }

        normalCapybara = if (!isDay) {
            val sleepingOriginal = BitmapFactory.decodeResource(resources, sleepingResId)
            Bitmap.createScaledBitmap(sleepingOriginal, targetWidth, targetHeight, true)
        } else {
            defaultBitmap
        }

        pettingCapybara = Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(resources, pettingResId),
            targetWidth, targetHeight, true
        )

        capybara = normalCapybara
        invalidate()
    }

    fun isPettable(x: Int, y: Int) : Boolean {
        return isDay && capybaraRect?.contains(x, y) == true
    }

    fun getModel(): PethomeModel {
        return pethomeModel
    }
}
