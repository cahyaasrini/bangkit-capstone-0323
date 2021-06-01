package com.cap0323.medy.ui.detail

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cap0323.medy.data.remote.response.MedicineResponse
import com.cap0323.medy.databinding.ActivityDetailBinding
import com.cap0323.medy.databinding.ItemsMedicineRecommendationBinding

class DetailAdapter : RecyclerView.Adapter<DetailAdapter.DetailViewHolder>() {
    private val detail = ArrayList<MedicineResponse>()

    fun setRecommendation(medicine: List<MedicineResponse>) {
        this.detail.clear()
        this.detail.addAll(medicine)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val itemsMedicineRecommendationBinding =
            ItemsMedicineRecommendationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailViewHolder(itemsMedicineRecommendationBinding)
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) =
        holder.bind(detail[position])

    override fun getItemCount(): Int = detail.size

    class DetailViewHolder(val binding: ItemsMedicineRecommendationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(medicine: MedicineResponse) {
            binding.apply {
                tvBrandName.text = medicine.brandName
                tvCategory.text = medicine.category
                tvDate.text = medicine.effectiveTime
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.idMedicine, medicine.id)
                    intent.putExtra(DetailActivity.categoryName, medicine.category)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}