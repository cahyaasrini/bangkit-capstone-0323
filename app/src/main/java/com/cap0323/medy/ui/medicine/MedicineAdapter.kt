package com.cap0323.medy.ui.medicine

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cap0323.medy.data.remote.response.MedicineResponse
import com.cap0323.medy.databinding.ItemMedicineNameBinding
import com.cap0323.medy.ui.detail.DetailActivity

class MedicineAdapter : RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder>() {
    private val listMedicine = ArrayList<MedicineResponse>()

    fun setMedicine(medicine: MedicineResponse) {
        this.listMedicine.clear()
        this.listMedicine.addAll(listOf(medicine))
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
    }

    override fun getItemCount(): Int = listMedicine.size

    class MedicineViewHolder(val binding: ItemMedicineNameBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(medicine: MedicineResponse) {
            with(binding) {
                tvShowName.text = medicine.brandName
                tvEffectiveTime.text = medicine.effectiveTime
                category.text = medicine.category

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.idMedicine, medicine.id)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}

