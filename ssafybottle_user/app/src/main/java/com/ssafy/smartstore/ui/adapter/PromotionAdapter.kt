package com.ssafy.smartstore.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.smartstore.data.dto.promotion.Promotion
import com.ssafy.smartstore.databinding.ItemPromotionBinding
import com.ssafy.smartstore.utils.dummyPromotions

class PromotionAdapter : RecyclerView.Adapter<PromotionAdapter.PromotionViewHolder>() {

    var promotions: List<Promotion> = dummyPromotions

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PromotionViewHolder {
        return PromotionViewHolder(
            ItemPromotionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PromotionViewHolder, position: Int) {
        holder.bind(promotions[position])
    }

    override fun getItemCount() = promotions.size

    class PromotionViewHolder(private val binding: ItemPromotionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(promotion: Promotion) {
            binding.promotion = promotion
        }
    }
}