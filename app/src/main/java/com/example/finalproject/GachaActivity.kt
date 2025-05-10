package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class GachaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gachaView = GachaView(this).apply {
            setTitle("You Won A Dog!")
            setRewardImage(R.drawable.dog) // Set the dog image
            setOnSelectClickListener {
                // Handle select action
                Toast.makeText(context, "Dog selected!", Toast.LENGTH_SHORT).show()
            }
            setOnProceedClickListener {
                // Handle proceed action
                startActivity(Intent(this@GachaActivity, MainActivity::class.java))
            }
        }

        setContentView(gachaView)
    }
}