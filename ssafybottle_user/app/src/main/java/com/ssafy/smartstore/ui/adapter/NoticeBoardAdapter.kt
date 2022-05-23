package com.ssafy.smartstore.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.smartstore.data.dto.notification.NotificationDto
import com.ssafy.smartstore.databinding.ItemNoticeBoardBinding

class NoticeBoardAdapter : RecyclerView.Adapter<NoticeBoardAdapter.NoticeBoardViewHolder>() {

    var notifications: List<NotificationDto> = emptyList()
    lateinit var itemClickListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeBoardViewHolder {

        return NoticeBoardViewHolder(
            ItemNoticeBoardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            bindOnItemClickListener(itemClickListener)
        }
    }

    override fun onBindViewHolder(holder: NoticeBoardViewHolder, position: Int) {
        holder.bind(notifications[position])
    }

    override fun getItemCount() = notifications.size

    class NoticeBoardViewHolder(private val binding: ItemNoticeBoardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(notification: NotificationDto) {
            binding.notification = notification
        }

        fun bindOnItemClickListener(itemClickListener: OnItemClickListener) {
            binding.imgItemNoticeboardCancel.setOnClickListener {
                itemClickListener.onItemClick(it, adapterPosition)
            }
        }
    }
}