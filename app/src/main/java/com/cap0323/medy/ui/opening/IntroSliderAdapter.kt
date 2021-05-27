package com.cap0323.medy.ui.opening

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cap0323.medy.data.IntroSliderData
import com.cap0323.medy.databinding.SlideItemContainerBinding

class IntroSliderAdapter(private val introSlider: List<IntroSliderData>) :
    RecyclerView.Adapter<IntroSliderAdapter.IntroSliderViewHolder>() {

    inner class IntroSliderViewHolder(val binding: SlideItemContainerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(introSliderData: IntroSliderData) {
            with(binding) {
                tvTitle.text = introSliderData.title
                tvDesc.text = introSliderData.description
                imageSlider.setAnimation(introSliderData.icon)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroSliderViewHolder {
        val itemsMovieBinding =
            SlideItemContainerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IntroSliderViewHolder(itemsMovieBinding)
    }

    override fun onBindViewHolder(holder: IntroSliderViewHolder, position: Int) {
        holder.bind(introSlider[position])
    }

    override fun getItemCount(): Int = introSlider.size
}