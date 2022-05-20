package com.ssafy.smartstore.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.smartstore.data.dto.order.OrderDetailDto
import com.ssafy.smartstore.databinding.ItemShoppingListBinding

class ShoppingListAdapter : RecyclerView.Adapter<ShoppingListAdapter.ShoppingViewHolder>() {

    var shoppingList: List<OrderDetailDto> = emptyList()
    lateinit var itemClickListener: OnItemClickListener

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShoppingViewHolder {
        return ShoppingViewHolder(
            ItemShoppingListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            bindOnItemClickListener(itemClickListener)
        }
    }

    override fun onBindViewHolder(holder: ShoppingViewHolder, position: Int) {
        holder.bind(shoppingList[position])
    }

    override fun getItemCount() = shoppingList.size

    class ShoppingViewHolder(private val binding: ItemShoppingListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(orderDetail: OrderDetailDto) {
            binding.item = orderDetail
        }

        fun bindOnItemClickListener(itemClickListener: OnItemClickListener) {
            binding.imgItemShoppinglistCancel.setOnClickListener {
                itemClickListener.onItemClick(it, adapterPosition)
            }
        }
    }
}