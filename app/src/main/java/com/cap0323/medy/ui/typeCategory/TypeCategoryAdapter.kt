package com.cap0323.medy.ui.typeCategory

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.cap0323.medy.R
import com.cap0323.medy.data.local.entity.TypeCategoryEntity
import com.cap0323.medy.databinding.ItemMedicineCategoryBinding

class TypeCategoryAdapter(private val context: Context) : RecyclerView.Adapter<TypeCategoryAdapter.CategoryViewHolder>() {

    private val listCategory = ArrayList<TypeCategoryEntity>()


    fun setCategory(category: List<TypeCategoryEntity>) {
        this.listCategory.clear()
        this.listCategory.addAll(category)
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
//        holder.binding.categoryImage.startAnimation(
//            AnimationUtils.loadAnimation(
//                context,
//                R.anim.fade_transition_animation
//            )
//        )
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