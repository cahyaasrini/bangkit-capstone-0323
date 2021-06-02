package com.cap0323.medy.ui.typeCategory

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.cap0323.medy.R
import com.cap0323.medy.data.remote.response.CategoryResponse
import com.cap0323.medy.data.remote.response.MedicineResponse
import com.cap0323.medy.databinding.ItemMedicineCategoryBinding
import com.cap0323.medy.databinding.ItemMedicineIndicationBinding
import com.cap0323.medy.ui.indication.IndicationActivity

class BottomSheetCategoryAdapter(private val context: Context) :
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
    }

    override fun getItemCount(): Int = category.size

    class IndicationViewHolder(val binding: ItemMedicineIndicationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(medicine: CategoryResponse) {
            binding.apply {
                tvIndication.text = medicine.indication
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, IndicationActivity::class.java)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}