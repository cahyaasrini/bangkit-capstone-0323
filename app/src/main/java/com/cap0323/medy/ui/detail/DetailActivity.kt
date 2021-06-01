package com.cap0323.medy.ui.detail

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cap0323.medy.R
import com.cap0323.medy.data.remote.response.MedicineResponse
import com.cap0323.medy.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var adapter: DetailAdapter
    private val detailViewModel: DetailViewModel by viewModels()

    companion object {
        const val idMedicine = "extra_id_medicine"
        const val categoryName = "category_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        statusBarColor()
        setUpRecylerView()
        viewModel()

        detailViewModel.detailMedicine.observe(this, {
            if (it != null) {
                val listMedicine = ArrayList<MedicineResponse>()
                listMedicine.clear()
                listMedicine.addAll(it)

                for (data in listMedicine) {
                    with(binding) {
                        tvBrandName.text = data.brandName
                        tvPurpose.text = data.purpose
                        category.text = data.category
                        date.text = data.effectiveTime
                        activeIngredient.text = data.activeIngredient
                        inactiveIngredient.text = data.inactiveIngredient
                        indication.text = data.indicationsAndUsage
                        dosageAdministration.text = data.dosageAndAdministration
                        warning.text = data.warnings
                        collapsingToolbar.title = getString(R.string.app_name)
                        collapsingToolbar.setExpandedTitleTextColor(getColorStateList(R.color.white))
                        collapsingToolbar.setCollapsedTitleTextColor(getColor(R.color.white))
                        collapsingToolbar.setContentScrimColor(getColor(R.color.color_btn))
                    }
                }
            }
        })

        detailViewModel.recommendationMedicine.observe(this, {
            if (it != null) {
                Log.d("data yang masuk", it.toString())
                adapter.setRecommendation(it)
            }
        })
    }

    private fun viewModel() {
        val extras = intent.extras
        if (extras != null) {
            val idMedicine = extras.getString(idMedicine)
            val categoryName = extras.getString(categoryName)
            if (idMedicine != null) {
                detailViewModel.getDetailMedicine(idMedicine)
            }
            if (categoryName != null) {
                detailViewModel.getRecommendation(categoryName)
            }
            Log.d("Testingfromhome", categoryName.toString())
        }
    }

    private fun setUpRecylerView() {
        binding.apply {
            val orientation = resources.configuration.orientation

            rvDetailMedicine.layoutManager =
                LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = DetailAdapter(this@DetailActivity)
            rvDetailMedicine.adapter = adapter
        }
    }

    private fun observeDetail() {

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