package com.example.finalproject
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.setMargins

class GachaView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val titleTextView: TextView
    private val rewardPlaceholder: View
    private val selectButton: Button
    private val proceedButton: Button

    init {
        // Set up the main container
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        setBackgroundColor(Color.parseColor("#F5F5F5"))

        // Create a vertical LinearLayout to hold all elements
        val mainLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            ).apply {
                setMargins(32)
            }
        }

        // Title Text
        titleTextView = TextView(context).apply {
            text = "Gacha Reward"
            textSize = 24f
            setTextColor(Color.BLACK)
            gravity = Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, 0, 32)
            }
        }

        // Reward Placeholder (square)
        rewardPlaceholder = View(context).apply {
            setBackgroundColor(Color.parseColor("#6200EE"))
            layoutParams = LinearLayout.LayoutParams(
                resources.getDimensionPixelSize(R.dimen.reward_size),
                resources.getDimensionPixelSize(R.dimen.reward_size)
            ).apply {
                setMargins(0, 0, 0, 32)
            }
        }

        // Button container (horizontal layout)
        val buttonContainer = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 32, 0, 0)
            }
        }

        // Select Button
        selectButton = Button(context).apply {
            text = "Select Reward"
            setBackgroundColor(Color.parseColor("#03DAC6"))
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, 16, 0)
            }
        }

        // Proceed Button
        proceedButton = Button(context).apply {
            text = "Proceed"
            setBackgroundColor(Color.parseColor("#3700B3"))
            setTextColor(Color.WHITE)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        // Add buttons to button container
        buttonContainer.addView(selectButton)
        buttonContainer.addView(proceedButton)

        // Add all views to main layout
        mainLayout.addView(titleTextView)
        mainLayout.addView(rewardPlaceholder)
        mainLayout.addView(buttonContainer)

        // Add main layout to this custom view
        addView(mainLayout)

        // Set up click listeners
        setupClickListeners()
    }

    private fun setupClickListeners() {
        selectButton.setOnClickListener {
            // Handle select reward button click
            // You might want to highlight the selected reward or store the selection
            rewardPlaceholder.setBackgroundColor(Color.parseColor("#FFD700")) // Gold color for selection
        }

        proceedButton.setOnClickListener {
            // Handle proceed button click
            // Typically this would navigate to another screen or show the next reward
            // For this example, we'll just reset the view
            rewardPlaceholder.setBackgroundColor(Color.parseColor("#6200EE"))
        }
    }

    // Public methods to customize the view
    fun setTitle(title: String) {
        titleTextView.text = title
    }

    fun setOnSelectClickListener(listener: OnClickListener) {
        selectButton.setOnClickListener(listener)
    }

    fun setOnProceedClickListener(listener: OnClickListener) {
        proceedButton.setOnClickListener(listener)
    }
}