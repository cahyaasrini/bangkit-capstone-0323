package com.cap0323.medy.ui.typeCategory

import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.cap0323.medy.R
import com.cap0323.medy.databinding.ActivityTypeCategoryBinding
import com.cap0323.medy.ui.medicine.MedicineAdapter

class TypeCategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTypeCategoryBinding
    private val typeCategoryViewModel: TypeCategoryViewModel by viewModels()
    private lateinit var adapter: TypeCategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTypeCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        statusBarColor()
        setUpRecylerView()

        typeCategoryViewModel.getQuery("")
        typeCategoryViewModel.getQueryLive.observe(this, {
            if (it != "") {
                typeCategoryViewModel.getCategoryByAbjad(it)
                typeCategoryViewModel.categoryList.observe(
                    this@TypeCategoryActivity,
                    { medicine ->
                        adapter.setCategoryItem(medicine)
                    })
            } else {
                displayingAllData()
            }
        })

        typeCategoryViewModel.isLoading.observe(this, {
            if (it) {
                binding.apply {
                    rvCategory.visibility = View.GONE
                    shimmer.visibility = View.VISIBLE
                    shimmer.startShimmer()
                }
            } else {
                binding.apply {
                    rvCategory.visibility = View.VISIBLE
                    shimmer.stopShimmer()
                    shimmer.visibility = View.GONE
                }
            }
        })

        typeCategoryViewModel.noData.observe(this, {
            if (it) {
                dataNotFound("visible")
                binding.rvCategory.visibility = View.INVISIBLE
            } else {
                dataNotFound("gone")
            }
        })

        binding.noData.btnOk.setOnClickListener {
            displayingAllData()
            dataNotFound("gone")
        }
    }

    private fun setUpRecylerView() {
        binding.apply {
            val orientation = resources.configuration.orientation

            rvCategory.layoutManager =
                LinearLayoutManager(this@TypeCategoryActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = TypeCategoryAdapter(this@TypeCategoryActivity)

            if (orientation == SCREEN_ORIENTATION_PORTRAIT) {
                binding.rvCategory.layoutManager = GridLayoutManager(this@TypeCategoryActivity, 3)
            } else {
                binding.rvCategory.layoutManager = GridLayoutManager(this@TypeCategoryActivity, 4)
            }
            binding.rvCategory.setHasFixedSize(true)
            adapter = TypeCategoryAdapter(this@TypeCategoryActivity)

            rvCategory.adapter = adapter
//            btmSheet.rvBtmSheet.adapter = adapter
        }
    }

    private fun displayingAllData() {
        typeCategoryViewModel.getAllCategory("all")
        typeCategoryViewModel.categoryListAll.observe(this, { cat ->
            adapter.setCategory(cat)
        })
    }

    private fun dataNotFound(status: String) {
        when (status) {
            "visible" -> binding.noData.noDataDialog.visibility = View.VISIBLE
            "gone" -> binding.noData.noDataDialog.visibility = View.GONE
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