package com.cap0323.medy.ui.typeCategory

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.cap0323.medy.R
import com.cap0323.medy.data.remote.response.MedicineResponse
import com.cap0323.medy.databinding.ItemMedicineCategoryBinding
import com.cap0323.medy.databinding.ItemsMedicineRecommendationBinding
import com.cap0323.medy.ui.detail.DetailActivity
import com.cap0323.medy.ui.indication.IndicationActivity

class BottomSheetCategoryAdapter(private val context: Context) :
    RecyclerView.Adapter<BottomSheetCategoryAdapter.CategoryViewHolder>() {
    private val category = ArrayList<MedicineResponse>()

    fun setBottomSheetAdapter(medicine: List<MedicineResponse>) {
        this.category.clear()
        this.category.addAll(medicine)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemMedicineCategoryBinding =
            ItemMedicineCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return CategoryViewHolder(itemMedicineCategoryBinding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(category[position])
        holder.binding.tvMovieImage.startAnimation(
            AnimationUtils.loadAnimation(
                context,
                R.anim.fragment_open_enter
            )
        )
    }

    override fun getItemCount(): Int = category.size

    class CategoryViewHolder(val binding: ItemMedicineCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(medicine: MedicineResponse) {
            binding.apply {
                tvCategory.text = medicine.category
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, IndicationActivity::class.java)
//                    intent.putExtra(IndicationActivity.idMedicine, medicine.id)
//                    intent.putExtra(IndicationActivity.categoryName, medicine.category)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}