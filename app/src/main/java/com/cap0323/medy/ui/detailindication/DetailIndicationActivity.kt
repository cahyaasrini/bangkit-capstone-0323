package com.cap0323.medy.ui.detailindication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cap0323.medy.R
import com.cap0323.medy.databinding.ActivityDetailIndicationBinding
import com.cap0323.medy.ui.medicinerecommendationbyindication.RecommendationActivity
import com.cap0323.medy.ui.typecategory.TypeCategoryActivity
import com.cap0323.medy.ui.typeselection.TypeSelectionActivity
import com.google.android.material.snackbar.Snackbar

class DetailIndicationActivity : AppCompatActivity() {
    private val viewModel: DetailIndicationViewModel by viewModels()
    private lateinit var adapter: DetailIndicationAdapter

    companion object {
        const val categoryName = "category_name"
    }

    private lateinit var binding: ActivityDetailIndicationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailIndicationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        statusBarColor()
        setUpRecyclerView()
        action()


        val extras = intent.extras
        if (extras != null) {
            val category = extras.getString(categoryName)
            Log.d("Print", category.toString())

            if (category != null) {
                viewModel.getIndicationByCategory(category)
                viewModel.detailByCategory.observe(this, {
                    adapter.setAdapter(it)
                })
            }
        }

        viewModel.noData.observe(this, {
            if (it) {
                dataNotFound("visible")
            } else {
                dataNotFound("gone")
            }
        })

        viewModel.isLoading.observe(this, {
            if (it) {
                binding.apply {
                    rvBtmSheet.visibility = View.GONE
                    shimmer.visibility = View.VISIBLE
                    imgBtmSheet.visibility = View.VISIBLE
                    shimmer.startShimmer()
                }
            } else {
                binding.apply {
                    rvBtmSheet.visibility = View.VISIBLE
                    shimmer.stopShimmer()
                    imgBtmSheet.visibility = View.GONE
                    shimmer.visibility = View.GONE
                }
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right)
    }

    private fun setUpRecyclerView() {
        binding.apply {
            rvBtmSheet.layoutManager = LinearLayoutManager(this@DetailIndicationActivity)
            adapter = DetailIndicationAdapter(this@DetailIndicationActivity)
            rvBtmSheet.adapter = adapter
        }
    }

    private fun dataNotFound(status: String) {
        when (status) {
            "visible" -> binding.noData.noDataDialog.visibility = View.VISIBLE
            "gone" -> binding.noData.noDataDialog.visibility = View.GONE
        }
    }

    private fun action() {
        binding.sending.setOnClickListener {
            val medicineBuilderWords = StringBuilder()
            if (adapter.selectedItems.size > 0) {
                for (i in adapter.selectedItems) {
                    medicineBuilderWords.append(i)
                    medicineBuilderWords.append(",")
                }
                medicineBuilderWords.deleteCharAt(medicineBuilderWords.lastIndex)

                Toast.makeText(
                    this@DetailIndicationActivity,
                    medicineBuilderWords,
                    Toast.LENGTH_SHORT
                ).show()

                val intent =
                    Intent(this@DetailIndicationActivity, RecommendationActivity::class.java)
                intent.putExtra(
                    RecommendationActivity.extraCategory,
                    medicineBuilderWords.toString()
                )
                this@DetailIndicationActivity.startActivity(intent)
            } else {
                Snackbar.make(window.decorView.rootView, R.string.choose, Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
        binding.delete.setOnClickListener {
            adapter.multiSelect = false
            adapter.selectedItems.clear()
            adapter.notifyDataSetChanged()
            Snackbar.make(
                window.decorView.rootView,
                "Deleted selected items successfully",
                Snackbar.LENGTH_SHORT
            ).show()
        }
        binding.cancelBtn.setOnClickListener{
            finish()
            overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right)
        }
    }

    private fun statusBarColor() {
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.color_btn)
    }
}