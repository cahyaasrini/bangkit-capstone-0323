package com.cap0323.medy.ui.indication

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cap0323.medy.R
import com.cap0323.medy.databinding.ActivityIndicationBinding

class IndicationActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_CATEGORY = "extra_category"
    }

    private lateinit var binding: ActivityIndicationBinding
    private val indicationViewModel: IndicationViewModel by viewModels()
    private lateinit var adapter: IndicationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIndicationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        statusBarColor()
        setUpRecylerView()

        supportActionBar?.apply {
            title = "Top 10 recommendation"
        }


        indicationViewModel.getQuery("")
        indicationViewModel.getQueryLive.observe(this, {
            if (it != "") {
                indicationViewModel.getMedicineByIndication(it)
                indicationViewModel.medicineListByIndication.observe(
                    this@IndicationActivity,
                    { medicine ->
                        adapter.setMedicine(medicine)
                    })
            } else {
                displayingAllData()
            }
        })

        indicationViewModel.isLoading.observe(this, {
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

        indicationViewModel.noData.observe(this, {
            if (it) {
                dataNotFound("visible")
                binding.rvMedicine.visibility = View.INVISIBLE
            } else {
                dataNotFound("gone")
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu_indication, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.info -> Toast.makeText(
                this@IndicationActivity,
                "Feature is available soon !",
                Toast.LENGTH_SHORT
            ).show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun displayingAllData() {
        val extras = intent.extras
        if (extras != null) {
            val category = extras.getString(EXTRA_CATEGORY)
            if (category != null) {
                indicationViewModel.getAllByCategory(category)
                indicationViewModel.allByCategory.observe(this, {
                    adapter.setMedicine(it)
                    Log.d("Testing api indication", it.toString())
                })
            }
        }
    }

    private fun setUpRecylerView() {
        binding.apply {
            rvMedicine.layoutManager = LinearLayoutManager(this@IndicationActivity)
            adapter = IndicationAdapter(this@IndicationActivity)
            rvMedicine.adapter = adapter
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

    private fun dataNotFound(status: String) {
        when (status) {
            "visible" -> binding.noData.noDataDialog.visibility = View.VISIBLE
            "gone" -> binding.noData.noDataDialog.visibility = View.GONE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}