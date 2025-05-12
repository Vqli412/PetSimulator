package com.example.finalproject

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.setMargins

class SettingsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val titleTextView: TextView
    private val soundSwitch: Switch
    private val themeSwitch: Switch
    private val backButton: Button

    init {
        setBackgroundColor(ContextCompat.getColor(context, R.color.stardew_bg)) // pastel background

        val mainLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(48, 48, 48, 48)
        }

        // Back Button
        backButton = Button(context).apply {
            text = "← Back"
            textSize = 16f
            setBackgroundColor(ContextCompat.getColor(context, R.color.stardew_accent))
            setTextColor(Color.WHITE)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.START
                setMargins(0, 0, 0, 24)
            }
        }

        titleTextView = TextView(context).apply {
            text = "Settings"
            textSize = 32f
            gravity = Gravity.CENTER
            setTextColor(ContextCompat.getColor(context, R.color.stardew_title))
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, 0, 32)
            }
        }

        // Containers
        val soundContainer = createToggleContainer("Sound Effects", "Enable or disable game sounds")
        soundSwitch = Switch(context).apply {
            isChecked = true
        }
        soundContainer.addView(soundSwitch)

        val themeContainer = createToggleContainer("Theme", "Toggle between day and night theme")
        themeSwitch = Switch(context).apply {
            isChecked = true
        }
        themeContainer.addView(themeSwitch)

        // Add everything to layout
        mainLayout.addView(backButton)
        mainLayout.addView(titleTextView)
        mainLayout.addView(soundContainer)
        mainLayout.addView(themeContainer)

        addView(mainLayout)
    }

    private fun createToggleContainer(title: String, subtitle: String): LinearLayout {
        return LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, 0, 32)
            }

            val textContainer = LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            }

            textContainer.addView(TextView(context).apply {
                text = title
                textSize = 20f
                setTextColor(ContextCompat.getColor(context, R.color.stardew_text))
            })

            textContainer.addView(TextView(context).apply {
                text = subtitle
                textSize = 14f
                setTextColor(ContextCompat.getColor(context, R.color.stardew_hint))
            })

            addView(textContainer)
        }
    }

    fun setTitle(title: String) {
        titleTextView.text = title
    }

    fun setOnSoundToggleChanged(listener: (Boolean) -> Unit) {
        soundSwitch.setOnCheckedChangeListener { _, isChecked -> listener(isChecked) }
    }

    fun setOnThemeToggleChanged(listener: (Boolean) -> Unit) {
        themeSwitch.setOnCheckedChangeListener { _, isChecked -> listener(isChecked) }
    }

    fun setInitialSettings(soundEnabled: Boolean, themeIsDay: Boolean) {
        soundSwitch.isChecked = soundEnabled
        themeSwitch.isChecked = themeIsDay
    }

    fun setOnBackButtonClicked(listener: () -> Unit) {
        backButton.setOnClickListener { listener() }
    }
}
