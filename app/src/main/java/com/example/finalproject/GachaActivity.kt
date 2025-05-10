package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class GachaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gachaView = GachaView(this).apply {
            setOnSelectClickListener {
                Toast.makeText(context, "Capybara selected!", Toast.LENGTH_SHORT).show()
            }
            setOnProceedClickListener {
                startActivity(Intent(this@GachaActivity, PethomeActivity::class.java))
            }
        }

        setContentView(gachaView)
    }
}
