package com.cap0323.medy.ui.typecategory

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.cap0323.medy.R
import com.cap0323.medy.data.remote.response.CategoryResponse
import com.cap0323.medy.databinding.ItemMedicineIndicationBinding
import com.cap0323.medy.ui.detailindication.DetailIndicationActivity

class BottomSheetCategoryAdapter(
    private val context: Context,
) :
    RecyclerView.Adapter<BottomSheetCategoryAdapter.IndicationViewHolder>() {

    private val category = ArrayList<CategoryResponse>()

    fun setBottomSheetAdapter(medicine: List<CategoryResponse>) {
        this.category.clear()
        this.category.addAll(medicine)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndicationViewHolder {
        val itemMedicineIndication =
            ItemMedicineIndicationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return IndicationViewHolder(itemMedicineIndication)
    }

    override fun onBindViewHolder(holder: IndicationViewHolder, position: Int) {
        holder.bind(category[position])
        holder.itemView.startAnimation(
            AnimationUtils.loadAnimation(
                context,
                R.anim.fade_scale_animation
            )
        )
    }

    override fun getItemCount(): Int = category.size

    class IndicationViewHolder(val binding: ItemMedicineIndicationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(medicine: CategoryResponse) {
            binding.apply {
                tvCategory.text = medicine.category
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailIndicationActivity::class.java)
                    intent.putExtra(DetailIndicationActivity.categoryName, medicine.category)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}