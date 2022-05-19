package com.ssafy.smartstore.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.smartstore.data.dto.product.ProductCommentDto
import com.ssafy.smartstore.data.entitiy.Comment
import com.ssafy.smartstore.databinding.ItemCommentBinding

class CommentAdapter(
    private val userId: String,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    var comments: List<ProductCommentDto> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(
            ItemCommentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            bindClickListeners(itemClickListener)
        }
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(comments[position], userId)
    }

    override fun getItemCount() = comments.size

    class CommentViewHolder(private val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(comment: ProductCommentDto, userId: String) {
            binding.comment = comment
            binding.userId = userId
        }

        fun bindClickListeners(onItemClickListener: OnItemClickListener) {
            binding.imgIcommentEdit.setOnClickListener {
                onItemClickListener.onItemClick(it, adapterPosition)
            }
        }
    }
}