package com.cap0323.medy.ui.typeCategory

import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.cap0323.medy.R
import com.cap0323.medy.databinding.ActivityTypeCategoryBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior


class TypeCategoryActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_ID = "extra_id"
    }

    private lateinit var binding: ActivityTypeCategoryBinding
    private val typeCategoryViewModel: TypeCategoryViewModel by viewModels()
    private lateinit var adapter: TypeCategoryAdapter
    private lateinit var adapterBottomSheetCategory: BottomSheetCategoryAdapter
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTypeCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        statusBarColor()
        setUpRecylerViewMain()
        setUpRecylerBottomSheet()
        displayingAllData()

        supportActionBar?.apply {
            title = "Select indication by Alphabet"
        }


        val extras = intent.extras
        if (extras != null) {
            val charCategory = extras.getString(EXTRA_ID)
            if (charCategory != null) {
                typeCategoryViewModel.getCategoryByChar(charCategory)
                typeCategoryViewModel.indicationByChar.observe(this, {
                    adapterBottomSheetCategory.setBottomSheetAdapter(it)
                })
                bottomSheetSetUp()
            }
        }

        typeCategoryViewModel.noData.observe(this, {
            if (it) {
                dataNotFound("visible")
            } else {
                dataNotFound("gone")
            }
        })

        typeCategoryViewModel.isLoading.observe(this, {
            if (it) {
                binding.apply {
                    btmSheet.rvBtmSheet.visibility = View.GONE
                    btmSheet.shimmer.visibility = View.VISIBLE
                    btmSheet.imgBtmSheet.visibility = View.VISIBLE
                    btmSheet.shimmer.startShimmer()
                }
            } else {
                binding.apply {
                    btmSheet.rvBtmSheet.visibility = View.VISIBLE
                    btmSheet.shimmer.stopShimmer()
                    btmSheet.imgBtmSheet.visibility = View.GONE
                    btmSheet.shimmer.visibility = View.GONE
                }
            }
        })
    }

    private fun setUpRecylerViewMain() {
        binding.apply {
            val orientation = resources.configuration.orientation
            if (orientation == SCREEN_ORIENTATION_PORTRAIT) {
                rvCategory.layoutManager = GridLayoutManager(this@TypeCategoryActivity, 2)
            } else {
                rvCategory.layoutManager = GridLayoutManager(this@TypeCategoryActivity, 4)
            }
            adapter = TypeCategoryAdapter(this@TypeCategoryActivity)
            rvCategory.adapter = adapter
        }
    }

    private fun setUpRecylerBottomSheet() {
        binding.apply {
            btmSheet.rvBtmSheet.layoutManager = LinearLayoutManager(this@TypeCategoryActivity)
            adapterBottomSheetCategory = BottomSheetCategoryAdapter(this@TypeCategoryActivity)
            btmSheet.rvBtmSheet.adapter = adapterBottomSheetCategory
        }
    }

    private fun bottomSheetSetUp() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.btmSheet.bottomSheet)
        if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        } else {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        binding.btmSheet.cancelBtn.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    private fun displayingAllData() {
        adapter.setCategory(typeCategoryViewModel.getAllCategory())
    }

    private fun statusBarColor() {
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.color_btn)
    }

    private fun dataNotFound(status: String) {
        when (status) {
            "visible" -> binding.btmSheet.noData.noDataDialog.visibility = View.VISIBLE
            "gone" -> binding.btmSheet.noData.noDataDialog.visibility = View.GONE
        }
    }
}