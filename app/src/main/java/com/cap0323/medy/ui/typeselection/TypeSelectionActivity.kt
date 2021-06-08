package com.cap0323.medy.ui.typeselection

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.cap0323.medy.R
import com.cap0323.medy.databinding.ActivityTypeSelectionBinding
import com.cap0323.medy.ui.medicine.MedicineActivity
import com.cap0323.medy.ui.typecategory.TypeCategoryActivity

class TypeSelectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTypeSelectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTypeSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        statusBarColor()
        binding.btnTypeMed.setOnClickListener {
            Intent(applicationContext, MedicineActivity::class.java).also {
                startActivity(it)
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right)
            }
        }

        binding.btnTypeIndication.setOnClickListener {
            Intent(applicationContext, TypeCategoryActivity::class.java).also {
                startActivity(it)
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
            }
        }
        startingAnimation()
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }

    private fun startingAnimation() {
        val delayTime = 1500L

        val animationFromTop = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        val animationFromLeft = AnimationUtils.loadAnimation(this, R.anim.enter_from_right_type_selection)
        val animationFromRight = AnimationUtils.loadAnimation(this, R.anim.enter_from_left_type_selection)

        with(binding) {
            imageView.animation = animationFromTop
            card1.animation = animationFromRight
            card2.animation = animationFromLeft
        }
    }

    private fun statusBarColor() {
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.color_btn)
    }
}