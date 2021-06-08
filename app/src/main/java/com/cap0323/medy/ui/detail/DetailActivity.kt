package com.cap0323.medy.ui.detail

import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.cap0323.medy.R
import com.cap0323.medy.data.remote.response.MedicineResponse
import com.cap0323.medy.databinding.ActivityDetailBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var adapter: DetailAdapter
    private lateinit var adapterBottomSheet: BottomSheetAdapter
    private val detailViewModel: DetailViewModel by viewModels()
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

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
        setupBackButton()
        viewModel()
        observeDetail()
        bottomSheetSetUp()
        collapsingToolbar()

        detailViewModel.recommendationMedicine.observe(this, {
            if (it != null) {
                Log.d("data yang masuk", it.toString())
                adapter.setRecommendation(it)
                //adapter.setRecommendation(it)
                adapterBottomSheet.setBottomSheetAdapter(it)
                adapter.setRecommendation(it)
            }
        })

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.navigationIcon
            ?.setColorFilter(resources.getColor(R.color.white), PorterDuff.Mode.SRC_ATOP)

        binding.btnFavorite.setOnClickListener {
            Toast.makeText(this@DetailActivity, "Feature is available soon !", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun setupBackButton() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.collapsingToolbar.setExpandedTitleColor(Color.TRANSPARENT)
    }

    private fun bottomSheetSetUp() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.btmSheet.bottomSheet)
        binding.btnAll.setOnClickListener {
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            } else {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }

        binding.btmSheet.cancelBtn.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun viewModel() {
        val extras = intent.extras
        if (extras != null) {
            val idMedicine = extras.getString(idMedicine)
            val categoryName = extras.getString(categoryName)
            if (idMedicine != null) {
                detailViewModel.getDetailMedicine(idMedicine)
                detailViewModel.getRecommendationHeroku(idMedicine)
                binding.collapsingToolbar.expandedTitleMarginBottom
            }

        }
    }

    private fun setUpRecylerView() {
        binding.apply {
            val orientation = resources.configuration.orientation

            if (orientation == SCREEN_ORIENTATION_PORTRAIT) {
                btmSheet.rvBtmSheet.layoutManager =
                    GridLayoutManager(this@DetailActivity, 2)
            } else {
                btmSheet.rvBtmSheet.layoutManager =
                    GridLayoutManager(this@DetailActivity, 3)
            }
            btmSheet.rvBtmSheet.setHasFixedSize(true)

            adapterBottomSheet = BottomSheetAdapter(this@DetailActivity)

            rvDetailMedicine.layoutManager =
                LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = DetailAdapter(this@DetailActivity)
            btmSheet.rvBtmSheet.adapter = adapter
            rvDetailMedicine.adapter = adapter
        }
    }

    private fun observeDetail() {
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
    }

    private fun statusBarColor() {
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.color_btn)
    }

    private fun collapsingToolbar() {
        binding.apply {
            collapsingToolbar.title = getString(R.string.detail)
            collapsingToolbar.setExpandedTitleTextColor(getColorStateList(R.color.white))
            collapsingToolbar.setCollapsedTitleTextColor(getColor(R.color.white))
            collapsingToolbar.setContentScrimColor(getColor(R.color.color_btn))
        }
    }
}