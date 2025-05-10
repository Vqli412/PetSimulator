package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class GachaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gachaView = GachaView(this).apply {
            setTitle("Your Gacha Reward")
            setOnSelectClickListener {
                // Handle select action
            }
            setOnProceedClickListener {
                // Handle proceed action
                startActivity(Intent(this@GachaActivity, MainActivity::class.java))
            }
        }

        setContentView(gachaView)
    }
}