package com.ssafy.smartstore.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.smartstore.data.dto.product.ProductDto
import com.ssafy.smartstore.data.entitiy.Product
import com.ssafy.smartstore.databinding.ItemMenuBinding

class MenuDetailAdapter : RecyclerView.Adapter<MenuDetailAdapter.MenuDetailViewHolder>() {

    var products: List<ProductDto> = emptyList()
    lateinit var onItemClickListener : (View, Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuDetailViewHolder {
        return MenuDetailViewHolder(
            ItemMenuBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            bindOnItemClickListener(onItemClickListener)
        }
    }

    override fun onBindViewHolder(holder: MenuDetailViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount() = products.size

    class MenuDetailViewHolder(private val binding: ItemMenuBinding) :
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