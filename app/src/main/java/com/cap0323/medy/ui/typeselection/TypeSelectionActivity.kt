package com.cap0323.medy.ui.typeselection

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.cap0323.medy.R
import com.cap0323.medy.databinding.ActivityTypeSelectionBinding
import com.cap0323.medy.ui.indication.IndicationActivity
import com.cap0323.medy.ui.medicine.MedicineActivity

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
            Intent(applicationContext, IndicationActivity::class.java).also {
                startActivity(it)
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
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