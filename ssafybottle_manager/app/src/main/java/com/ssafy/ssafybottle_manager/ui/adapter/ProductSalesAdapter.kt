package com.ssafy.ssafybottle_manager.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.ssafybottle_manager.data.dto.product.ProductDto
import com.ssafy.ssafybottle_manager.data.dto.product.ProductSalesDto
import com.ssafy.ssafybottle_manager.databinding.ItemProductBinding
import com.ssafy.ssafybottle_manager.databinding.ItemProductSalesBinding

class ProductSalesAdapter : RecyclerView.Adapter<ProductSalesAdapter.ProductSalesViewHolder>() {
    var productSales: List<ProductSalesDto> = emptyList()
    lateinit var onItemClickListener: (View, Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductSalesViewHolder {
        return ProductSalesViewHolder(
            ItemProductSalesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            bindOnItemClickListener(onItemClickListener)
        }
    }

    override fun onBindViewHolder(holder: ProductSalesViewHolder, position: Int) {
        holder.bind(productSales[position])
    }

    override fun getItemCount() = productSales.size

    class ProductSalesViewHolder(private val binding: ItemProductSalesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(productSales: ProductSalesDto) {
            binding.item = productSales
        }

        fun bindOnItemClickListener(onItemClickListener: (View, Int) -> Unit) {
            binding.root.setOnClickListener {
                onItemClickListener(it, adapterPosition)
            }
        }
    }
}