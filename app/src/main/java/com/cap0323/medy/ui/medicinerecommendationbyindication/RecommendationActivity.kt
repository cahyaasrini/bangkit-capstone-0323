package com.cap0323.medy.ui.medicinerecommendationbyindication

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cap0323.medy.R
import com.cap0323.medy.databinding.ActivityIndicationBinding
import java.util.*

class RecommendationActivity : AppCompatActivity() {

    companion object {
        const val extraCategory = "extra_category"
    }

    private lateinit var binding: ActivityIndicationBinding
    private val recommendationViewModel: RecommendationViewModel by viewModels()
    private lateinit var adapter: RecommendationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIndicationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        statusBarColor()
        setUpRecylerView()

        supportActionBar?.apply {
            title = "Top 10 recommendation"
            setDisplayHomeAsUpEnabled(true)
        }


        recommendationViewModel.getQuery("")
        recommendationViewModel.getQueryLive.observe(this, {
            if (it != "") {
                recommendationViewModel.getMedicineByIndication(it)
                recommendationViewModel.medicineListByIndication.observe(
                    this@RecommendationActivity,
                    { medicine ->
                        adapter.setMedicine(medicine)
                    })
            } else {
                displayingAllData()
            }
        })

        recommendationViewModel.isLoading.observe(this, {
            if (it) {
                binding.apply {
                    rvMedicine.visibility = View.GONE
                    shimmer.visibility = View.VISIBLE
                    shimmer.startShimmer()
                }
            } else {
                binding.apply {
                    rvMedicine.visibility = View.VISIBLE
                    shimmer.stopShimmer()
                    shimmer.visibility = View.GONE
                }
            }
        })

        recommendationViewModel.noData.observe(this, {
            if (it) {
                dataNotFound("visible")
                binding.rvMedicine.visibility = View.INVISIBLE
            } else {
                dataNotFound("gone")
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun displayingAllData() {
        val extras = intent.extras
        if (extras != null) {
            val category = extras.getString(extraCategory)
            if (category != null) {
                recommendationViewModel.getAllByCategory(category)
                recommendationViewModel.allByCategory.observe(this, {
                    adapter.setMedicine(it)
                    Log.d("Testing api indication", it.toString())
                })
            }
            supportActionBar?.apply {
                title = "Top 10 Recommendation"
                subtitle = "of ${category?.capitalize(Locale.ROOT)}"
                setDisplayHomeAsUpEnabled(true)
            }

        }
    }

    private fun setUpRecylerView() {
        binding.apply {
            rvMedicine.layoutManager = LinearLayoutManager(this@RecommendationActivity)
            adapter = RecommendationAdapter(this@RecommendationActivity)
            rvMedicine.adapter = adapter
        }
    }

    private fun statusBarColor() {
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.color_btn)
    }

    private fun dataNotFound(status: String) {
        when (status) {
            "visible" -> binding.noData.noDataDialog.visibility = View.VISIBLE
            "gone" -> binding.noData.noDataDialog.visibility = View.GONE
        }
    }
}