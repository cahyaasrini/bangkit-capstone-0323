package com.cap0323.medy.ui.detail

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.cap0323.medy.R
import com.cap0323.medy.data.remote.response.MedicineResponse
import com.cap0323.medy.databinding.ItemsMedicineRecommendationBinding

class BottomSheetAdapter(private val context: Context) :
    RecyclerView.Adapter<BottomSheetAdapter.DetailViewHolder>() {
    private val detail = ArrayList<MedicineResponse>()

    fun setBottomSheetAdapter(medicine: List<MedicineResponse>) {
        this.detail.clear()
        this.detail.addAll(medicine)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val itemsMedicineRecommendationBinding =
            ItemsMedicineRecommendationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return DetailViewHolder(itemsMedicineRecommendationBinding)
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.bind(detail[position])
        holder.binding.tvMovieImage.startAnimation(
            AnimationUtils.loadAnimation(
                context,
                R.anim.fragment_open_enter
            )
        )
    }

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