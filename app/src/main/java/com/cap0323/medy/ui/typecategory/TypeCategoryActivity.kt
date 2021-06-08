package com.cap0323.medy.ui.typecategory

import android.content.Intent
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.cap0323.medy.R
import com.cap0323.medy.databinding.ActivityTypeIndicationBinding
import com.cap0323.medy.ui.detailindication.DetailIndicationFragment
import com.cap0323.medy.ui.medicinerecommendationbyindication.RecommendationActivity
import com.cap0323.medy.ui.medicine.InformationDialogFragment
import com.cap0323.medy.ui.typeselection.TypeSelectionActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar

class TypeCategoryActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_ID = "extra_id"
        const val alphabet = "alphabet"
    }

    private lateinit var binding: ActivityTypeIndicationBinding
    private val typeCategoryViewModel: TypeCategoryViewModel by viewModels()
    private lateinit var adapter: TypeCategoryAdapter
    private lateinit var adapterBottomSheetCategory: BottomSheetCategoryAdapter
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTypeIndicationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        statusBarColor()
        setUpRecyclerViewMain()
        setUpRecyclerBottomSheet()
        displayingAllData()


        supportActionBar?.apply {
            title = "Indication List"
            subtitle = "Select by alphabet"
            setDisplayHomeAsUpEnabled(true)
        }


        val extras = intent.extras
        if (extras != null) {
            val charCategory = extras.getString(EXTRA_ID)
            val alphabet = extras.getString(alphabet)

            binding.btmSheet.title.text = alphabet.toString()

            if (charCategory != null) {
                typeCategoryViewModel.getCategoryByChar(charCategory)
                typeCategoryViewModel.categoryByChar.observe(this, {
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

    override fun onBackPressed() {
        val intent = Intent(this@TypeCategoryActivity, TypeSelectionActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right)
        super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu_indication, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.info -> {
                val infoMainText = resources.getText(R.string.info_detail_indication)
                val mOptionDialogFragment = InformationDialogFragment(infoMainText as String)
                mOptionDialogFragment.show(
                    supportFragmentManager,
                    InformationDialogFragment::class.java.simpleName
                )
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpRecyclerViewMain() {
        binding.apply {
            val orientation = resources.configuration.orientation
            if (orientation == SCREEN_ORIENTATION_PORTRAIT) {
                rvTypeIndication.layoutManager = GridLayoutManager(this@TypeCategoryActivity, 2)
            } else {
                rvTypeIndication.layoutManager = GridLayoutManager(this@TypeCategoryActivity, 4)
            }
            adapter = TypeCategoryAdapter(this@TypeCategoryActivity)
            rvTypeIndication.adapter = adapter
        }
    }

    private fun setUpRecyclerBottomSheet() {
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

//        binding.btmSheet.sending.setOnClickListener {
//            val medicineBuilderWords = StringBuilder()
//            if (adapterBottomSheetCategory.selectedItems.size > 0) {
//                for (i in adapterBottomSheetCategory.selectedItems) {
//                    medicineBuilderWords.append(i)
//                    medicineBuilderWords.append(",")
//                }
//                medicineBuilderWords.deleteCharAt(medicineBuilderWords.lastIndex)
//
//                Toast.makeText(
//                    this@TypeCategoryActivity,
//                    medicineBuilderWords,
//                    Toast.LENGTH_SHORT
//                ).show()

//                val intent = Intent(this@TypeCategoryActivity, RecommendationActivity::class.java)
//                intent.putExtra(RecommendationActivity.extraCategory, medicineBuilderWords.toString())
//                this@TypeCategoryActivity.startActivity(intent)
//                val mOptionDialogFragment = DetailIndicationFragment()
//                mOptionDialogFragment.show(
//                    supportFragmentManager,
//                    DetailIndicationFragment::class.java.simpleName
//                )
//            } else {
//                Snackbar.make(window.decorView.rootView, R.string.choose, Snackbar.LENGTH_SHORT)
//                    .show()
//            }
//        }
//        binding.btmSheet.delete.setOnClickListener {
//            adapterBottomSheetCategory.multiSelect = false
//            adapterBottomSheetCategory.selectedItems.clear()
//            adapterBottomSheetCategory.notifyDataSetChanged()
//            Snackbar.make(
//                window.decorView.rootView,
//                "Deleted selected items successfully",
//                Snackbar.LENGTH_SHORT
//            ).show()
//        }
    }

    private fun displayingAllData() {
        adapter.setCategory(typeCategoryViewModel.getAllIndication())
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