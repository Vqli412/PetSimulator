package com.example.finalproject

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class PethomeActivity : AppCompatActivity() {
    private lateinit var pethomeView : PethomeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) { //we want to get decorView to set the height of the rect for height
        super.onWindowFocusChanged(hasFocus)
        buildViewByCode()
    }

    fun buildViewByCode() {
        var width: Int = resources.displayMetrics.widthPixels
        var height: Int = resources.displayMetrics.heightPixels
        var rectangle: Rect = Rect(0, 0, 0, 0)
        window.decorView.getWindowVisibleDisplayFrame(rectangle)
        var statusBar: Int = rectangle.top

        //add adview bar below, subtract from height

        pethomeView = PethomeView(this, width, height - statusBar)
        setContentView(pethomeView)
    }
}