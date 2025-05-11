package com.example.finalproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create the SettingsView
        val settingsView = SettingsView(this).apply {
            setTitle("Game Settings")

            // Load saved preferences (you would typically use SharedPreferences)
            setInitialSettings(
                soundEnabled = true,  // Default value
                themeIsDay = true  // Default value
            )

            // Set up toggle listeners
            setOnSoundToggleChanged { isChecked ->
                // Save sound preference
                // Example: sharedPreferences.edit().putBoolean("sound_enabled", isChecked).apply()
            }

            setOnThemeToggleChanged { isChecked ->
                // Save notifications preference
                // Example: sharedPreferences.edit().putBoolean("notifications_enabled", isChecked).apply()
            }
        }

        setContentView(settingsView)
    }
}