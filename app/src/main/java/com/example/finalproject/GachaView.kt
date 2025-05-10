package com.example.finalproject
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.setMargins
import kotlin.random.Random

class GachaView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val titleTextView: TextView
    private val rewardImageView: ImageView
    private val selectButton: Button
    private val proceedButton: Button

    private var clickCount = 0
    private var lastRewardRes: Int = 0
    private val petDrawables = listOf(
        R.drawable.orange_capy_default,
        R.drawable.boba_capy_default,
        R.drawable.clover_capy_default
    )


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

        // Reward ImageView (replaces the placeholder)
        rewardImageView = ImageView(context).apply {
            setImageResource(R.drawable.giftbox_closed) // Set default image
            scaleType = ImageView.ScaleType.CENTER_CROP
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
        mainLayout.addView(rewardImageView)
        mainLayout.addView(buttonContainer)

        // Add main layout to this custom view
        addView(mainLayout)

        // Initialize the click logic on the box image
        setupBoxClicks()

        // Set up click listeners
        setupClickListeners()
    }

    private fun setupBoxClicks() {
        rewardImageView.setOnClickListener {
            clickCount++
            when (clickCount) {
                in 1..2 -> {
                    //jiggle box on 1st and 2nd click
                    ObjectAnimator.ofFloat(
                        rewardImageView,
                        "translationX",
                        0f, 25f, -25f, 15f, -15f, 0f
                    ).apply {
                        duration = 300L
                        start()
                    }
                }
                3 -> {
                    // show opened box on the 3rd click
                    rewardImageView.setImageResource(R.drawable.giftbox_open)
                    postDelayed({
                       randomRewardImage()
                        rewardImageView.apply {
                            alpha = 0f
                            scaleX = 0.8f
                            scaleY = 0.8f
                            animate()
                                .alpha(1f)
                                .scaleX(1f)
                                .scaleY(1f)
                                .setDuration(400L)
                                .start()
                        }
                        setTitle(randomRewardMessage())
                    }, 800L)
                }
                else -> {
                    Log.w("MainActivity", "box already opened")
                }
            }
        }
    }


    private fun setupClickListeners() {
        selectButton.setOnClickListener {
            // Visual feedback for selection
            rewardImageView.setColorFilter(Color.argb(150, 255, 215, 0)) // Gold tint
        }

        proceedButton.setOnClickListener {
            // Remove selection effect when proceeding
            rewardImageView.clearColorFilter()
        }
    }

    // Public methods to customize the view
    fun setTitle(title: String) {
        titleTextView.text = title
    }

    fun randomRewardImage() {
        lastRewardRes = petDrawables[Random.nextInt(petDrawables.size)]
        rewardImageView.setImageResource(lastRewardRes)
    }

    fun randomRewardMessage(): String  {
        var res = ""
        if (lastRewardRes == petDrawables[0]) {
            res = "You got Orange Capybara!"
        } else if (lastRewardRes == petDrawables[1]) {
            res = "You got Boba Capybara!"
        } else if (lastRewardRes == petDrawables[2]) {
            res = "You got Clover Capybara!"
        }
        return res
    }

    fun setOnSelectClickListener(listener: OnClickListener) {
        selectButton.setOnClickListener(listener)
    }

    fun setOnProceedClickListener(listener: OnClickListener) {
        proceedButton.setOnClickListener(listener)
    }
}