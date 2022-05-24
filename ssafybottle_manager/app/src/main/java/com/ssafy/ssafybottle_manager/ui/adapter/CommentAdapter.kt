package com.ssafy.ssafybottle_manager.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.ssafybottle_manager.data.dto.product.ProductCommentDto
import com.ssafy.ssafybottle_manager.databinding.ItemCommentBinding

class CommentAdapter : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    var comments: List<ProductCommentDto> = emptyList()
    lateinit var onItemClickListener : (View, Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(
            ItemCommentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            bindClickListeners(onItemClickListener)
        }
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(comments[position])
    }

    override fun getItemCount() = comments.size

    class CommentViewHolder(private val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(comment: ProductCommentDto) {
            binding.comment = comment
        }

        fun bindClickListeners(onItemClickListener : (View, Int) -> Unit) {
            binding.imgItemCommentEdit.setOnClickListener {
                onItemClickListener(it, adapterPosition)
            }
        }
    }
}