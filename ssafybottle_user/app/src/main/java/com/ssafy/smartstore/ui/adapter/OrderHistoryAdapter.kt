package com.ssafy.smartstore.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.smartstore.data.dto.order.OrderByUserDto
import com.ssafy.smartstore.data.entitiy.RecentOrder
import com.ssafy.smartstore.databinding.ItemOrderHistoryBinding

class OrderHistoryAdapter : RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder>() {

    var orderHistories: List<OrderByUserDto> = emptyList()
    lateinit var onItemClickListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHistoryViewHolder {
        return OrderHistoryViewHolder(
            ItemOrderHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            bindOnItemClickListeners(onItemClickListener)
        }
    }

    override fun onBindViewHolder(holder: OrderHistoryViewHolder, position: Int) {
        holder.bind(orderHistories[position])
    }

    override fun getItemCount() = orderHistories.size

    class OrderHistoryViewHolder(private val binding: ItemOrderHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(orderHistory: OrderByUserDto) {
            binding.orderHistory = orderHistory
        }

        fun bindOnItemClickListeners(onItemClickListener: OnItemClickListener) {
            binding.root.setOnClickListener {
                onItemClickListener.onItemClick(it, adapterPosition)
            }
        }
    }
}