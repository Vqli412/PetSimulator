package com.example.finalproject

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val prefs = getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val isDay = prefs.getBoolean(KEY_THEME_IS_DAY, true)

        // Apply theme before super.onCreate
        if (!isDay) {
            setTheme(R.style.AppTheme_Night)
        } else {
            setTheme(R.style.AppTheme_Day)
        }

        super.onCreate(savedInstanceState)

        val settingsView = SettingsView(this).apply {
            setTitle("Game Settings")

            setInitialSettings(soundEnabled = true, themeIsDay = isDay)

            setOnSoundToggleChanged {
                // Save sound preference
            }

            setOnThemeToggleChanged { isChecked ->
                prefs.edit().putBoolean(KEY_THEME_IS_DAY, isChecked).apply()
                recreate()
            }

            setOnBackButtonClicked {
                finish() // Go back to PethomeActivity or previous screen
            }
        }

        setContentView(settingsView)
    }

    companion object {
        const val PREFS = "game_prefs"
        const val KEY_THEME_IS_DAY = "theme_is_day"
    }
}
