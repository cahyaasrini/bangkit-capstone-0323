package com.cap0323.medy.ui.typeIndication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cap0323.medy.data.remote.response.IndicationResponse
import com.cap0323.medy.databinding.ItemMedicineIndicationBinding

class BottomSheetIndicationAdapter(
    private val context: Context,
) :
    RecyclerView.Adapter<BottomSheetIndicationAdapter.IndicationViewHolder>() {

    private var multiSelect = false

    var selectedItems = arrayListOf<String>()

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
        val currentData = category[position].indication.toString()
        holder.bind(category[position])
        if (selectedItems.contains(currentData)) {
            holder.binding.check.visibility = View.VISIBLE
        } else {
            holder.binding.check.visibility = View.GONE
        }

        holder.itemView.setOnLongClickListener {
            if (!multiSelect) {

                multiSelect = true

                selectItem(holder, currentData)
            }
            true
        }

        holder.itemView.setOnClickListener {
            if (multiSelect) {
                selectItem(holder, currentData)
            } else {
            }
        }
    }

    private fun selectItem(holder: IndicationViewHolder, data: String) {
        if (selectedItems.contains(data)) {
            selectedItems.remove(data)
            holder.binding.check.visibility = View.GONE
        } else {
            selectedItems.add(data)
            holder.binding.check.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int = category.size

    class IndicationViewHolder(val binding: ItemMedicineIndicationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(medicine: IndicationResponse) {
            binding.apply {
                tvIndication.text = medicine.indication
            }
        }
    }
}