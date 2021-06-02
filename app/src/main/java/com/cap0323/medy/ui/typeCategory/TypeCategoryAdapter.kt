package com.cap0323.medy.ui.typeCategory

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.cap0323.medy.R
import com.cap0323.medy.data.local.entity.TypeCategoryEntity
import com.cap0323.medy.data.remote.response.CategoryResponse
import com.cap0323.medy.data.remote.response.CategoryResponseItem
import com.cap0323.medy.databinding.ItemMedicineCategoryBinding
import com.cap0323.medy.ui.detail.DetailActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior

class TypeCategoryAdapter(private val context: Context) :
    RecyclerView.Adapter<TypeCategoryAdapter.CategoryViewHolder>() {
    private val listCategory = ArrayList<TypeCategoryEntity>()
    private val listCategoryItem = ArrayList<CategoryResponseItem>()

    fun setCategory(category: ArrayList<TypeCategoryEntity>) {
        this.listCategory.clear()
        this.listCategory.addAll(category)
        notifyDataSetChanged()
    }

    fun setCategoryItem(category: List<CategoryResponseItem>) {
        this.listCategoryItem.clear()
        this.listCategoryItem.addAll(category)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {
        val itemsMedicineBinding =
            ItemMedicineCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(itemsMedicineBinding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(listCategory[position])
        holder.binding.categoryImage.startAnimation(
            AnimationUtils.loadAnimation(
                context,
                R.anim.fade_transition_animation
            )
        )
        holder.itemView.startAnimation(
            AnimationUtils.loadAnimation(
                context,
                R.anim.fade_scale_animation
            )
        )

    }

    override fun getItemCount(): Int = listCategory.size

    class CategoryViewHolder(val binding: ItemMedicineCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: TypeCategoryEntity) {
//            Log.d("Testing", category.toString())
            binding.apply {
                tvCategory.text = category.abjadCategory
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, TypeCategoryActivity::class.java)
                    intent.putExtra(TypeCategoryActivity.EXTRA_ID, category.typeCategory)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}