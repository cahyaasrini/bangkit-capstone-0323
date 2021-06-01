package com.cap0323.medy.ui.detail

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.cap0323.medy.data.remote.response.MedicineResponse
import com.cap0323.medy.databinding.ActivityDetailBinding
import com.cap0323.medy.ui.medicine.MedicineAdapter

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

            rvDetailMedicine.layoutManager = LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = DetailAdapter()
            rvDetailMedicine.adapter = adapter
        }
    }


    private fun observeDetail() {

    }
}