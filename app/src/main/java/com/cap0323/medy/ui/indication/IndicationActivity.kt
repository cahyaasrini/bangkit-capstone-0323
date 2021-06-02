package com.cap0323.medy.ui.indication

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cap0323.medy.R
import com.cap0323.medy.databinding.ActivityIndicationBinding
import com.cap0323.medy.ui.detail.DetailActivity
import com.cap0323.medy.ui.detail.DetailAdapter

class IndicationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIndicationBinding
    private lateinit var adapter: IndicationAdapter
    private val indicationViewModel: IndicationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIndicationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        statusBarColor()

//        indicationViewModel.indicateMedicine.observe(this, {
//            if (it != null) {
//                val listMedicine = ArrayList<MedicineResponse>()
//                listMedicine.clear()
//                listMedicine.addAll(it)
//
//                for (data in listMedicine) {
//                    with(binding) {
//
//                    }
//                }
//            }
//        }
    }

    private fun viewModel() {
        val extras = intent.extras
        if (extras != null) {
            val idMedicine = extras.getString(DetailActivity.idMedicine)
            val categoryName = extras.getString(DetailActivity.categoryName)
            if (idMedicine != null) {
//                indicationViewModel.getDetailMedicine(idMedicine)
            }
            if (categoryName != null) {
//                indicationViewModel.getRecommendation(categoryName)
            }
            Log.d("Testingfromhome", categoryName.toString())
        }
    }

    private fun setUpRecylerView() {
        binding.apply {
            val orientation = resources.configuration.orientation

//            rvDetailMedicine.layoutManager =
//                LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
//            adapter = DetailAdapter(this@DetailActivity)
//            rvDetailMedicine.adapter = adapter
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