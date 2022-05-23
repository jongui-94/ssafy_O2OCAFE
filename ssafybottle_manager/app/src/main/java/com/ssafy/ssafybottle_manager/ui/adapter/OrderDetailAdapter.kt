package com.ssafy.ssafybottle_manager.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.ssafybottle_manager.data.dto.order.OrderIdDetailDto
import com.ssafy.ssafybottle_manager.databinding.ItemOrderDetailBinding

class OrderDetailAdapter : RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewHolder>() {
    var orderDetails: List<OrderIdDetailDto> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailViewHolder {
        return OrderDetailViewHolder(
            ItemOrderDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OrderDetailViewHolder, position: Int) {
        holder.bind(orderDetails[position])
    }

    override fun getItemCount() = orderDetails.size

    class OrderDetailViewHolder(private val binding: ItemOrderDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(orderDetail: OrderIdDetailDto) {
            binding.item = orderDetail
        }
    }
}