package com.cap0323.medy.ui.typeIndication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cap0323.medy.data.remote.response.IndicationResponse
import com.cap0323.medy.databinding.ItemMedicineIndicationBinding
import com.cap0323.medy.ui.indication.IndicationActivity

class BottomSheetIndicationAdapter(private val context: Context) :
    RecyclerView.Adapter<BottomSheetIndicationAdapter.IndicationViewHolder>() {
    private val category = ArrayList<IndicationResponse>()

    fun setBottomSheetAdapter(medicine: List<IndicationResponse>) {
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
        fun bind(medicine: IndicationResponse) {
            binding.apply {
                tvIndication.text = medicine.indication
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, IndicationActivity::class.java)
                    intent.putExtra(IndicationActivity.extraCategory, medicine.indication)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}