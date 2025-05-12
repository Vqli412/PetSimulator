package com.example.finalproject

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.MotionEvent
import android.view.View

class PethomeView : View {
    private lateinit var paint: Paint
    private lateinit var daytime: Bitmap
    private lateinit var nighttime: Bitmap

    private lateinit var settings: Bitmap
    private val settingsRect = Rect()
    private var settingsClickListener: (() -> Unit)? = null

    private var width: Int = 0
    private var height: Int = 0
    private var useNight = false

    private var capybara: Bitmap? = null
    private var normalCapybara: Bitmap? = null
    private var pettingCapybara: Bitmap? = null
    private var capybaraRect: Rect? = null
    private var onCapybaraTouched: (() -> Unit)? = null

    private var isDraggingCapybara = false
    private var isPetting = false


    constructor(context: Context, width: Int, height: Int) : super(context) {
        paint = Paint()
        daytime = BitmapFactory.decodeResource(resources, R.drawable.daytime)
        nighttime = BitmapFactory.decodeResource(resources, R.drawable.nighttime)
        settings = BitmapFactory.decodeResource(resources, R.drawable.settings)
        this.width = width
        this.height = height
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw background
        val bg = if (useNight) nighttime else daytime
        val srcRect = Rect(0, 0, bg.width, bg.height)
        val dstRect = Rect(0, 0, width, height)
        canvas.drawBitmap(bg, srcRect, dstRect, paint)

        // Draw capybara (normal or petting)
        val toDraw = if (isPetting && pettingCapybara != null) pettingCapybara else normalCapybara
        toDraw?.let {
            val left = bg.width / 5
            val top = (bg.height / 1.75).toInt()
            canvas.drawBitmap(it, left.toFloat(), top.toFloat(), paint)
            capybaraRect = Rect(left, top, left + it.width, top + it.height)
        }
    }

    fun toggleDayNight() {
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

        normalCapybara = Bitmap.createScaledBitmap(original, targetWidth, targetHeight, true)
        capybara = normalCapybara

        val pettingResId = when (resId) {
            R.drawable.boba_capy_default -> R.drawable.boba_capy_petting
            R.drawable.clover_capy_default -> R.drawable.clover_capy_petting
            R.drawable.orange_capy_default -> R.drawable.orange_capy_petting
            else -> resId
        }

        val pettingOriginal = BitmapFactory.decodeResource(resources, pettingResId)
        pettingCapybara = Bitmap.createScaledBitmap(pettingOriginal, targetWidth, targetHeight, true)

        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x.toInt()
        val y = event.y.toInt()

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (capybaraRect?.contains(x, y) == true) {
                    isDraggingCapybara = true
                    isPetting = true
                    onCapybaraTouched?.invoke()
                    invalidate()
                    return true
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (isDraggingCapybara) {
                    onCapybaraTouched?.invoke()
                    return true
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                isDraggingCapybara = false
                isPetting = false
                invalidate()
            }
        }

        return super.onTouchEvent(event)
    }

    fun setOnCapybaraTouchedListener(listener: () -> Unit) {
        onCapybaraTouched = listener
    }
}
