package com.ssafy.smartstore.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.smartstore.data.dto.product.ProductDto
import com.ssafy.smartstore.databinding.ItemRecommendMenuBinding

class RecommendMenuAdapter : RecyclerView.Adapter<RecommendMenuAdapter.RecommendMenuViewHolder>() {

    var products: List<ProductDto> = emptyList()
    lateinit var onItemClickListener : (View, Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendMenuViewHolder {
        return RecommendMenuViewHolder(
            ItemRecommendMenuBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            bindOnItemClickListener(onItemClickListener)
        }
    }

    override fun onBindViewHolder(holder: RecommendMenuViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount() = products.size

    class RecommendMenuViewHolder(private val binding: ItemRecommendMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductDto) {
            binding.product = product
        }

        fun bindOnItemClickListener(onItemClickListener: (View, Int) -> Unit ) {
            binding.root.setOnClickListener {
                onItemClickListener(it, binding.product!!.id)
            }
        }
    }
}