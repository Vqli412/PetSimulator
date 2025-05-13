package com.example.finalproject

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        val prefs = getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val isDay = prefs.getBoolean(KEY_THEME_IS_DAY, true)
        val isSoundOn = prefs.getBoolean(KEY_SOUND_IS_ON, true)

        // Apply theme before super.onCreate
        if (!isDay) {
            setTheme(R.style.AppTheme_Night)
        } else {
            setTheme(R.style.AppTheme_Day)
        }

        if (!isSoundOn) {

        } else {

        }

        super.onCreate(savedInstanceState)

        val settingsView = SettingsView(this).apply {
            setTitle("Game Settings")

            setInitialSettings(soundEnabled = isSoundOn, themeIsDay = isDay)

            setOnSoundToggleChanged { isChecked ->
                // Save sound preference
                Log.w("SettingsActivity","isChecked =" + isChecked)
                prefs.edit().putBoolean(KEY_SOUND_IS_ON, isChecked).apply()
                recreate()
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
        const val KEY_SOUND_IS_ON = "sound_is_on"
    }
}
