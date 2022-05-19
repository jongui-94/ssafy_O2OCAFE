package com.ssafy.smartstore.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.smartstore.data.entitiy.ShoppingItem
import com.ssafy.smartstore.databinding.ItemShoppingListBinding

class ShoppingListAdapter : RecyclerView.Adapter<ShoppingListAdapter.ShoppingViewHolder>() {

    var shoppingList: List<ShoppingItem> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShoppingListAdapter.ShoppingViewHolder {
        return ShoppingListAdapter.ShoppingViewHolder(
            ItemShoppingListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ShoppingListAdapter.ShoppingViewHolder, position: Int) {
        holder.bind(shoppingList[position])
    }

    override fun getItemCount() = shoppingList.size

    class ShoppingViewHolder(private val binding: ItemShoppingListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(shoppingItem: ShoppingItem) {
            binding.item = shoppingItem
        }
    }
}