package com.cap0323.medy.ui.medicine

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.cap0323.medy.R
import com.cap0323.medy.databinding.ActivityMedicineBinding
import com.cap0323.medy.ui.typeselection.TypeSelectionActivity

class MedicineActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMedicineBinding
    private val medicineViewModel: MedicineViewModel by viewModels()
    private lateinit var adapter: MedicineAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMedicineBinding.inflate(layoutInflater)
        setContentView(binding.root)
        statusBarColor()
        setUpRecylerView()

        supportActionBar?.apply {
            title = "Medicines List"
            setDisplayHomeAsUpEnabled(true)
        }

        medicineViewModel.getQuery("")
        medicineViewModel.getQueryLive.observe(this, {
            if (it != "") {
                medicineViewModel.getMedicineByName(it)
                medicineViewModel.medicineListByName.observe(
                    this@MedicineActivity,
                    { medicine ->
                        adapter.setMedicine(medicine)
                    })
            } else {
                displayingAllData()
            }
        })

        medicineViewModel.isLoading.observe(this, {
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

        medicineViewModel.noData.observe(this, {
            if (it) {
                dataNotFound("visible")
                binding.rvMedicine.visibility = View.INVISIBLE
            } else {
                dataNotFound("gone")
            }
        })

        binding.noData.btnOk.setOnClickListener {
            displayingAllData()
            dataNotFound("gone")
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this@MedicineActivity, TypeSelectionActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
        super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setUpRecylerView() {
        binding.apply {
            rvMedicine.layoutManager = LinearLayoutManager(this@MedicineActivity)
            adapter = MedicineAdapter(this@MedicineActivity)
            rvMedicine.adapter = adapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                medicineViewModel.getMedicineByName(query)
                medicineViewModel.getQuery(query)
                medicineViewModel.medicineListByName.observe(
                    this@MedicineActivity,
                    {
                        if (it.isNotEmpty()) {
                            adapter.setMedicine(it)
                        } else {
                            binding.rvMedicine.visibility = View.GONE
                        }
                    })
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    displayingAllData()
                    dataNotFound("gone")
                }
                return true
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.info -> {
                Toast.makeText(
                    this@MedicineActivity,
                    "Feature is available soon !",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun displayingAllData() {
        medicineViewModel.getAllMedicine("all")
        medicineViewModel.medicineList.observe(this, { med ->
            adapter.setMedicine(med)
        })
    }

    private fun dataNotFound(status: String) {
        when (status) {
            "visible" -> binding.noData.noDataDialog.visibility = View.VISIBLE
            "gone" -> binding.noData.noDataDialog.visibility = View.GONE
        }
    }

    private fun statusBarColor() {
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.color_btn)
    }
}
