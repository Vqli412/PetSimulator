package com.example.finalproject

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs       = getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val savedIsDay  = prefs.getBoolean(KEY_THEME_IS_DAY, true)

        // Create the SettingsView
        val settingsView = SettingsView(this).apply {
            setTitle("Game Settings")

            // Load saved preferences (you would typically use SharedPreferences)
            setInitialSettings(
                soundEnabled = true, //default value
                themeIsDay   = savedIsDay
            )

            // Set up toggle listeners
            setOnSoundToggleChanged { isChecked ->
                // Save sound preference
                // Example: sharedPreferences.edit().putBoolean("sound_enabled", isChecked).apply()
            }

            setOnThemeToggleChanged { isChecked ->
                val prefs = getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                prefs.edit()
                    .putBoolean(KEY_THEME_IS_DAY, isChecked)
                    .apply()
            }
        }

        setContentView(settingsView)
    }

    companion object {
        val PREFS = "game_prefs"
        val KEY_THEME_IS_DAY = "theme_is_day"
    }
}