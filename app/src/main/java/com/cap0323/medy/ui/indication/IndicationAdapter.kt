package com.cap0323.medy.ui.indication

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.cap0323.medy.R
import com.cap0323.medy.data.remote.response.MedicineResponse
import com.cap0323.medy.databinding.ItemMedicineNameBinding
import com.cap0323.medy.ui.detail.DetailActivity
import com.cap0323.medy.ui.detail.DetailAdapter

class IndicationAdapter(private val context: Context):
    RecyclerView.Adapter<IndicationAdapter.MedicineViewHolder>() {
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
            ItemMedicineNameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    class MedicineViewHolder(val binding: ItemMedicineNameBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(medicine: MedicineResponse) {
            Log.d("Testing", medicine.toString())
            with(binding) {
                tvShowName.text = medicine.brandName
                tvEffectiveTime.text = medicine.effectiveTime
                category.text = medicine.category
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