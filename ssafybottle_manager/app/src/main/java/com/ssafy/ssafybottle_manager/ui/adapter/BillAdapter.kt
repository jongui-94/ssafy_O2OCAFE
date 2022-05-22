package com.ssafy.ssafybottle_manager.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.ssafybottle_manager.data.dto.order.OrderDetailDto
import com.ssafy.ssafybottle_manager.databinding.ItemBillBinding

class BillAdapter : RecyclerView.Adapter<BillAdapter.OrderViewHolder>() {
    var orderList: List<OrderDetailDto> = emptyList()
    lateinit var onItemClickListener : (View, Int) -> Unit
    lateinit var onItemChangeListener : (View, Int, Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder(
            ItemBillBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            bindOnItemClickListener(onItemClickListener, onItemChangeListener)
        }
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(orderList[position])
    }

    override fun getItemCount() = orderList.size

    class OrderViewHolder(private val binding: ItemBillBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(orderDetail: OrderDetailDto) {
            binding.item = orderDetail
        }

        fun bindOnItemClickListener(onItemClickListener: (View, Int) -> Unit, onItemChangeListener : (View, Int, Int) -> Unit ) {
            binding.imgItemBillCancel.setOnClickListener {
                onItemClickListener(it, adapterPosition)
            }
            binding.imgItemBillMinus.setOnClickListener {
                if(binding.item!!.quantity < 2) {
                    return@setOnClickListener
                }
                binding.item!!.quantity--
                onItemChangeListener(it, adapterPosition, binding.item!!.quantity)
                //notifyItemChanged(adapterPosition)
            }
            binding.imgItemBillPlus.setOnClickListener {
                binding.item!!.quantity++
                onItemChangeListener(it, adapterPosition, binding.item!!.quantity)
                //notifyItemChanged(adapterPosition)
            }
        }
    }
}