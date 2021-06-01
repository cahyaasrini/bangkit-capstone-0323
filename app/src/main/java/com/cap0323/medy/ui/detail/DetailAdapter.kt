package com.cap0323.medy.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cap0323.medy.data.remote.response.MedicineResponse
import com.cap0323.medy.databinding.ActivityDetailBinding

class DetailAdapter : RecyclerView.Adapter<DetailAdapter.DetailViewHolder>() {
    private val detail = ArrayList<MedicineResponse>()

    fun setDetail(medicine: List<MedicineResponse>) {
        this.detail.clear()
        this.detail.addAll(medicine)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val activityDetailBinding =
            ActivityDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailViewHolder(activityDetailBinding)
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) =
        holder.bind(detail[position])

    override fun getItemCount(): Int = detail.size

    class DetailViewHolder(val binding: ActivityDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(medicine: MedicineResponse) {
            binding.apply {
                tvBrandName.text = medicine.brandName
                tvPurpose.text = medicine.purpose
                category.text = medicine.category
                date.text = medicine.effectiveTime
                activeIngredient.text = medicine.activeIngredient
                inactiveIngredient.text = medicine.inactiveIngredient
                indication.text = medicine.indicationsAndUsage
                dosageAdministration.text = medicine.dosageAndAdministration
                warning.text = medicine.warnings
            }
        }
    }
}