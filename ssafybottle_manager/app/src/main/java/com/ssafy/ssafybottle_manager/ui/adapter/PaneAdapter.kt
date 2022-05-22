package com.ssafy.ssafybottle_manager.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.ssafybottle_manager.R
import com.ssafy.ssafybottle_manager.data.dto.pane.PaneMenu
import com.ssafy.ssafybottle_manager.databinding.ItemPaneBinding
import com.ssafy.ssafybottle_manager.databinding.ItemPaneTitleBinding
import com.ssafy.ssafybottle_manager.utils.*

class PaneAdapter(var menus: List<PaneMenu>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var onItemClickListener : (View, Int) -> Unit

    override fun getItemViewType(position: Int): Int {
        return menus[position].id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == MENU_TITLE) {
            return PaneTitleViewHolder(
                ItemPaneTitleBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        } else {
            return PaneViewHolder(
                ItemPaneBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            ).apply {
                bindClickListener(onItemClickListener)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(menus[position].id == MENU_TITLE) {
            (holder as PaneTitleViewHolder).bind(menus[position])
        } else {
            (holder as PaneViewHolder).bind(menus[position])
        }
    }

    override fun getItemCount() = menus.size

    class PaneViewHolder(private val binding : ItemPaneBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PaneMenu) {
            binding.apply {
                menu = item
            }
        }

        fun bindClickListener(onItemClickListener : (View, Int) -> Unit) {
            binding.root.setOnClickListener {
                onItemClickListener(it, adapterPosition)
            }
        }
    }

    class PaneTitleViewHolder(private val binding : ItemPaneTitleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PaneMenu) {
            binding.apply {
                menu = item
            }
        }
    }
}