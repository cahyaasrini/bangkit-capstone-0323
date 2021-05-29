package com.cap0323.medy.ui.medicine

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.cap0323.medy.R
import com.cap0323.medy.databinding.ActivityMedicineBinding

class MedicineActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMedicineBinding
    private val medicineViewModel: MedicineViewModel by viewModels()
    private lateinit var adapter: MedicineAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMedicineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecylerView()

//        medicineViewModel.getQueryLive.observe(this, { query ->
//            if (query != null) {
//                medicineViewModel.getMedicineByName(query)
//            }
//        })

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
    }

    private fun setUpRecylerView() {
        binding.apply {
            rvMedicine.layoutManager = LinearLayoutManager(this@MedicineActivity)
            adapter = MedicineAdapter()
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
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                   binding.rvMedicine.visibility = View.GONE
                } else {
                    medicineViewModel.getMedicineByName(newText)
                    medicineViewModel.getQuery(newText)
                    medicineViewModel.medicineListByName.observe(
                        this@MedicineActivity,
                        { medicine ->
                            if (medicine != null) {
                                adapter.setMedicine(medicine)
                            } else {
                                binding.rvMedicine.visibility = View.GONE
                                Toast.makeText(
                                    this@MedicineActivity,
                                    "Data Not Found",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })
                }
                return true
            }
        })
        return true
    }
}
