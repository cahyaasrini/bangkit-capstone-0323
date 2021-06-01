package com.cap0323.medy.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.cap0323.medy.data.remote.response.MedicineResponse
import com.cap0323.medy.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var adapter: DetailAdapter
    private val detailViewModel: DetailViewModel by viewModels()

    companion object {
        const val idMedicine = "extra_id_medicine"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel()

        detailViewModel.detailMedicine.observe(this, {
            if (it != null) {
                val listMedicine = ArrayList<MedicineResponse>()
                listMedicine.clear()
                listMedicine.addAll(it)

                for (data in listMedicine) {
                    with(binding) {
                        tvBrandName.text = data.brandName
                        data.brandName?.let { it1 -> Log.d("Testing", it1) }
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
    }

    private fun viewModel() {
        val extras = intent.extras
        if (extras != null) {
            val idMedicine = extras.getString(idMedicine)
            Log.d("Test", idMedicine.toString())
            if (idMedicine != null) {
                detailViewModel.getDetailMedicine(idMedicine)
            }
        }
    }

    private fun observeDetail() {

    }
}