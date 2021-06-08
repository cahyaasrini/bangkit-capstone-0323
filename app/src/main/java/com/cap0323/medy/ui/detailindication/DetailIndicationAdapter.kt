package com.cap0323.medy.ui.detailindication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.cap0323.medy.R
import com.cap0323.medy.data.remote.response.ConditionResponse
import com.cap0323.medy.databinding.FragmentDetailIndicationItemBinding

class DetailIndicationAdapter(
    private val context: Context,
) :
    RecyclerView.Adapter<DetailIndicationAdapter.IndicationViewHolder>() {

    var multiSelect = false

    var selectedItems = arrayListOf<String>()

    private val category = ArrayList<ConditionResponse>()

    fun setAdapter(medicine: List<ConditionResponse>) {
        this.category.clear()
        this.category.addAll(medicine)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IndicationViewHolder {
        val itemMedicineIndication =
            FragmentDetailIndicationItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return IndicationViewHolder(itemMedicineIndication)
    }

    override fun onBindViewHolder(
        holder: IndicationViewHolder,
        position: Int
    ) {
        val currentData = category[position].condition.toString()
        holder.bind(category[position])
        holder.itemView.startAnimation(
            AnimationUtils.loadAnimation(
                context,
                R.anim.fade_scale_animation
            )
        )

        if (selectedItems.contains(currentData)) {
            holder.binding.check.visibility = View.VISIBLE
            holder.binding.cvItemCourse.alpha = 0.7f
        } else {
            holder.binding.check.visibility = View.GONE
            holder.binding.cvItemCourse.alpha = 1.0f
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
            holder.binding.cvItemCourse.alpha = 1.0f
        } else {
            selectedItems.add(data)
            holder.binding.check.visibility = View.VISIBLE
            holder.binding.cvItemCourse.alpha = 0.7f
        }
    }

    override fun getItemCount(): Int = category.size

    class IndicationViewHolder(val binding: FragmentDetailIndicationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(medicine: ConditionResponse) {
            binding.apply {
                tvCategory.text = medicine.condition
            }
        }
    }
}