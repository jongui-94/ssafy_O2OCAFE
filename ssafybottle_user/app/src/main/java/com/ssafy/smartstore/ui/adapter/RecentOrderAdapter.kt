package com.ssafy.smartstore.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.smartstore.data.dto.order.OrderByUserDto
import com.ssafy.smartstore.databinding.ItemRecentOrderBinding

class RecentOrderAdapter : RecyclerView.Adapter<RecentOrderAdapter.RecentOrderViewHolder>() {

    var recentOrders: List<OrderByUserDto> = emptyList()
    lateinit var onItemClickListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentOrderViewHolder {
        return RecentOrderViewHolder(
            ItemRecentOrderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            bindOnItemClickListeners(onItemClickListener)
        }
    }

    override fun onBindViewHolder(holder: RecentOrderViewHolder, position: Int) {
        holder.bind(recentOrders[position])
    }

    override fun getItemCount() = recentOrders.size

    class RecentOrderViewHolder(private val binding: ItemRecentOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recentOrder: OrderByUserDto) {
            binding.recentOrder = recentOrder
        }

        fun bindOnItemClickListeners(onItemClickListener: OnItemClickListener) {
            binding.root.setOnClickListener {
                onItemClickListener.onItemClick(it, adapterPosition)
            }
        }
    }
}