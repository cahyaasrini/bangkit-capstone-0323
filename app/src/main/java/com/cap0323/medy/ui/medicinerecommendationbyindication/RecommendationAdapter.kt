package com.cap0323.medy.ui.medicinerecommendationbyindication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.cap0323.medy.R
import com.cap0323.medy.data.remote.response.MedicineResponse
import com.cap0323.medy.databinding.ItemMedicineByIndicationBinding
import com.cap0323.medy.ui.detail.DetailActivity
import java.math.RoundingMode
import java.text.DecimalFormat

class RecommendationAdapter(private val context: Context) :
    RecyclerView.Adapter<RecommendationAdapter.MedicineViewHolder>() {
    private val listMedicine = ArrayList<MedicineResponse>()

    fun setMedicine(medicine: List<MedicineResponse>) {
        this.listMedicine.clear()
        this.listMedicine.addAll(medicine)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MedicineViewHolder {
        val itemsMedicineBinding =
            ItemMedicineByIndicationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return MedicineViewHolder(itemsMedicineBinding)
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        holder.bind(listMedicine[position])
        holder.binding.imageBrand.startAnimation(
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

    override fun getItemCount(): Int = listMedicine.size

    class MedicineViewHolder(val binding: ItemMedicineByIndicationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(medicine: MedicineResponse) {
            val df = DecimalFormat("#.###")
            df.roundingMode = RoundingMode.CEILING
            with(binding) {
                tvShowName.text = medicine.brandName
                tvEffectiveTime.text = medicine.effectiveTime
                category.text = medicine.category
                tvPrecisionDetail.text = df.format(medicine.precisionScore?.toFloat())
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