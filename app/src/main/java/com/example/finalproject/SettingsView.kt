package com.example.finalproject

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.core.view.setMargins

class SettingsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val titleTextView: TextView
    private val soundSwitch: Switch
    private val themeSwitch: Switch

    init {
        // Set up the root layout
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        setPadding(32, 32, 32, 32)

        // Create a vertical LinearLayout to hold all elements
        val mainLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        }

        // Title Text
        titleTextView = TextView(context).apply {
            text = "Settings"
            textSize = 24f
            gravity = Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, 0, 32)
            }
        }

        // Sound Toggle
        val soundContainer = createToggleContainer(
            context,
            "Sound Effects",
            "Enable or disable game sounds"
        )
        soundSwitch = Switch(context).apply {
            isChecked = true
        }
        (soundContainer.layoutParams as LinearLayout.LayoutParams).setMargins(0, 0, 0, 16)
        soundContainer.addView(soundSwitch)

        // Theme Toggle
        val themeContainer = createToggleContainer(
            context,
            "Theme",
            "Set day or night theme"
        )
        themeSwitch = Switch(context).apply {
            isChecked = true
        }
        themeContainer.addView(themeSwitch)

        // Add all views to main layout
        mainLayout.addView(titleTextView)
        mainLayout.addView(soundContainer)
        mainLayout.addView(themeContainer)

        // Add main layout to this custom view
        addView(mainLayout)
    }

    private fun createToggleContainer(
        context: Context,
        title: String,
        subtitle: String
    ): LinearLayout {
        return LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            // Text container
            val textContainer = LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                layoutParams = LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f
                )
            }

            // Title
            TextView(context).apply {
                text = title
                textSize = 18f
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                textContainer.addView(this)
            }

            // Subtitle
            TextView(context).apply {
                text = subtitle
                textSize = 12f
                setTextColor(resources.getColor(android.R.color.darker_gray))
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0, 4, 0, 0)
                }
                textContainer.addView(this)
            }

            addView(textContainer)
        }
    }

    fun setTitle(title: String) {
        titleTextView.text = title
    }

    fun setOnSoundToggleChanged(listener: (Boolean) -> Unit) {
        soundSwitch.setOnCheckedChangeListener { _, isChecked ->
            listener(isChecked)
        }
    }

    fun setOnThemeToggleChanged(listener: (Boolean) -> Unit) {
        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            listener(isChecked)
        }
    }

    fun setInitialSettings(soundEnabled: Boolean, themeIsDay: Boolean) {
        soundSwitch.isChecked = soundEnabled
        themeSwitch.isChecked = themeIsDay
    }
}