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

class TypeCategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTypeCategoryBinding
    private val typeCategoryViewModel: TypeCategoryViewModel by viewModels()
    private lateinit var adapter: TypeCategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTypeCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        statusBarColor()


    }

//    private fun setUpRecylerView() {
//        binding.apply {
//            val orientation = resources.configuration.orientation
//
//            rvCategory.layoutManager =
//                LinearLayoutManager(this@TypeCategoryActivity, LinearLayoutManager.HORIZONTAL, false)
//            adapter = TypeCategoryAdapter(this@TypeCategoryActivity)
//
//            if (orientation == SCREEN_ORIENTATION_PORTRAIT) {
//                binding.rvCategory.layoutManager = GridLayoutManager(this@TypeCategoryActivity, 3)
//            } else {
//                binding.rvCategory.layoutManager = GridLayoutManager(this@TypeCategoryActivity, 4)
//            }
//            binding.rvCategory.setHasFixedSize(true)
//            adapter = TypeCategoryAdapter(this@TypeCategoryActivity)
//
//            rvCategory.adapter = adapter
////            btmSheet.rvBtmSheet.adapter = adapter
//        }
//    }

    private fun statusBarColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.color_btn)
        }
    }
}