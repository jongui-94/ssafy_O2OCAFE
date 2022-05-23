package com.ssafy.ssafybottle_manager.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.ssafybottle_manager.data.dto.order.OrderListDto
import com.ssafy.ssafybottle_manager.databinding.ItemOrderListBinding

class OrderManagementAdapter :
    RecyclerView.Adapter<OrderManagementAdapter.OrderManagementViewHolder>() {
    var orders: List<OrderListDto> = emptyList()
    lateinit var onItemClickListener: (View, Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderManagementViewHolder {
        var binding = ItemOrderListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return OrderManagementViewHolder(binding).apply {
            bindOnItemClickListener(onItemClickListener)
        }
    }

    override fun onBindViewHolder(holder: OrderManagementViewHolder, position: Int) {
        holder.bind(orders[position])
    }

    override fun getItemCount() = orders.size

    class OrderManagementViewHolder(private val binding: ItemOrderListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(order: OrderListDto) {
            binding.item = order
        }

        fun bindOnItemClickListener(onItemClickListener: (View, Int) -> Unit) {
            binding.root.setOnClickListener {
                onItemClickListener(it, adapterPosition)
            }
        }
    }
}