package com.cap0323.medy.ui.opening

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.cap0323.medy.R
import com.cap0323.medy.data.IntroSliderData
import com.cap0323.medy.databinding.ActivityMainBinding
import com.cap0323.medy.ui.typeselection.TypeSelectionActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val introSliderAdapter = IntroSliderAdapter(
        listOf(
            IntroSliderData(
                "List of Drugs",
                "Display the right variety of drugs according to the needs of the user",
                R.raw.medicine2
            ),
            IntroSliderData(
                "User",
                "This application can be used by various circles either the general public or health authorities",
                R.raw.worker2
            ),
            IntroSliderData(
                "Purpose",
                "Recommend the drug according to the needs and indications experienced by the user",
                R.raw.purpose
            )
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sliderViewPager.adapter = introSliderAdapter

        setupIndicator()
        statusBarColor()
        setCurrentIndicator(0)
        binding.sliderViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })
        binding.btnNext.setOnClickListener {
            binding.apply {
                if (sliderViewPager.currentItem + 1 < introSliderAdapter.itemCount) {
                    sliderViewPager.currentItem += 1
                } else {
                    Intent(applicationContext, TypeSelectionActivity::class.java).also {
                        startActivity(it)
                        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
                    }
                }
            }
        }
        binding.tvSkip.setOnClickListener {
            Intent(applicationContext, TypeSelectionActivity::class.java).also {
                startActivity(it)
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
            }
        }

    }

    private fun setupIndicator() {
        val indicators = arrayOfNulls<ImageView>(introSliderAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
                this?.layoutParams = layoutParams
            }
            binding.indicatorsContainer.addView(indicators[i])
        }
    }

    private fun setCurrentIndicator(index: Int) {
        val childCount = binding.indicatorsContainer.childCount
        for (i in 0 until childCount) {
            val imageView = binding.indicatorsContainer[i] as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
            }

        }
    }

    private fun statusBarColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.color_btn)
        }
    }
}