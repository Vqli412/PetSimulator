package com.example.finalproject

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.widget.*
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

    private var lastRewardRes: Int = 0
    private var lastRewardMessage: String = ""
    private var isBoxOpened = false

    private val petDrawables = listOf(
        R.drawable.orange_capy_default,
        R.drawable.boba_capy_default,
        R.drawable.clover_capy_default
    )

    init {
        // Layout setup
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        setBackgroundColor(Color.parseColor("#F5F5F5"))

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

        titleTextView = TextView(context).apply {
            text = "Gacha Reward - shake or click the button to open!"
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

        rewardImageView = ImageView(context).apply {
            setImageResource(R.drawable.giftbox_closed)
            scaleType = ImageView.ScaleType.CENTER_CROP
            layoutParams = LinearLayout.LayoutParams(
                resources.getDimensionPixelSize(R.dimen.reward_size),
                resources.getDimensionPixelSize(R.dimen.reward_size)
            ).apply {
                setMargins(0, 0, 0, 32)
            }
        }

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

        proceedButton = Button(context).apply {
            text = "Proceed"
            setBackgroundColor(Color.parseColor("#3700B3"))
            setTextColor(Color.WHITE)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        buttonContainer.addView(selectButton)
        buttonContainer.addView(proceedButton)

        mainLayout.addView(titleTextView)
        mainLayout.addView(rewardImageView)
        mainLayout.addView(buttonContainer)

        addView(mainLayout)

        setupClickListeners()
    }

    fun openBox() {
        if (!isBoxOpened) {
            isBoxOpened = true
            // Shake effect
            postDelayed({ObjectAnimator.ofFloat(
                rewardImageView,
                "translationX",
                0f, 25f, -25f, 15f, -15f, 0f
            ).apply {
                duration = 300L
                start()
            }}, 1000L)

            // Reveal reward after delay
            postDelayed({
                rewardImageView.setImageResource(R.drawable.giftbox_open)
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
            }, 600L)
        } else {
            Log.d("GachaView", "Box already opened.")
        }
    }

    private fun setupClickListeners() {
        selectButton.setOnClickListener {
            if (!isBoxOpened) {
                // Shake effect
                ObjectAnimator.ofFloat(
                    rewardImageView,
                    "translationX",
                    0f, 25f, -25f, 15f, -15f, 0f
                ).apply {
                    duration = 300L
                    start()
                }

                // Reveal reward after delay
                postDelayed({
                    rewardImageView.setImageResource(R.drawable.giftbox_open)
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
                    isBoxOpened = true
                }, 600L)
            } else {
                Log.d("GachaView", "Box already opened.")
            }
        }

        proceedButton.setOnClickListener {
            rewardImageView.clearColorFilter()
            onProceedListener?.invoke(lastRewardRes, lastRewardMessage)
        }
    }

    private fun randomRewardImage() {
        lastRewardRes = petDrawables.random()
        // modify the firebase user information
        rewardImageView.setImageResource(lastRewardRes)
    }

    private fun randomRewardMessage(): String {
        lastRewardMessage = when (lastRewardRes) {
            R.drawable.orange_capy_default -> "You got Orange Capybara!"
            R.drawable.boba_capy_default -> "You got Boba Capybara!"
            R.drawable.clover_capy_default -> "You got Clover Capybara!"
            else -> "You got a Capybara!"
        }
        return lastRewardMessage
    }

    fun setTitle(title: String) {
        titleTextView.text = title
    }

    // Callback for Proceed button
    private var onProceedListener: ((Int, String) -> Unit)? = null

    fun setOnProceedListener(listener: (capyResId: Int, capyName: String) -> Unit) {
        onProceedListener = listener
    }
}
