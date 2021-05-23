package com.cap0323.medy

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.cap0323.medy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val introSliderAdapter = IntroSliderAdapter(
        listOf(
            IntroSliderData(
                "List Obat",
                "Menampilkan variasi obat yang tepat sesuai dengan kebutuhan pengguna",
                R.raw.medicine2
            ),
            IntroSliderData(
                "Pengguna",
                "Aplikasi ini dapat digunakan oleh berbagai kalangan baik masyarakat umum atau pihak kesehatan",
                R.raw.worker2
            ),
            IntroSliderData(
                "Tujuan",
                "Merekomendasikan obat sesuai dengan kebutuhan dan indikasi yang dialami pengguna",
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
                    Intent(applicationContext, HomeSelectionActivity::class.java).also {
                        startActivity(it)
                    }
                }
            }
        }
        binding.tvSkip.setOnClickListener {
            Intent(applicationContext, HomeSelectionActivity::class.java).also {
                startActivity(it)
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
}