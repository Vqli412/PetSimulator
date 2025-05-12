package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class GachaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gachaView = GachaView(this).apply {
            setOnProceedListener { capyResId, capyName ->
                if (capyResId == 0) {
                    Toast.makeText(context, "Please select a reward first!", Toast.LENGTH_SHORT).show()
                } else {
                    val intent = Intent(context, PethomeActivity::class.java).apply {
                        putExtra("capyResId", capyResId)
                        putExtra("capyName", capyName)
                    }
                    context.startActivity(intent)
                }
            }
        }

        setContentView(gachaView)
    }
}
