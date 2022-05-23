package com.ssafy.ssafybottle_manager.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.ssafybottle_manager.data.dto.notification.NotificationDto
import com.ssafy.ssafybottle_manager.data.dto.product.ProductDto
import com.ssafy.ssafybottle_manager.databinding.ItemNotificationBinding
import com.ssafy.ssafybottle_manager.databinding.ItemProductBinding

class NotificationAdapter : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {
    //var notifications: List<NotificationDto> = emptyList()
    lateinit var onItemClickListener : (View, Int) -> Unit

    var notifications: List<NotificationDto> = listOf(
        NotificationDto(0, "", "주문접수", "한상우님의 주문이 접수되었습니다.", ""),
        NotificationDto(0, "", "주문접수", "한상우님의 주문이 접수되었습니다.", ""),
        NotificationDto(0, "", "주문접수", "한상우님의 주문이 접수되었습니다.", ""),
        NotificationDto(0, "", "주문접수", "한상우님의 주문이 접수되었습니다.", ""),
        NotificationDto(0, "", "주문접수", "한상우님의 주문이 접수되었습니다.", ""),
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        return NotificationViewHolder(
            ItemNotificationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            bindOnItemClickListener(onItemClickListener)
        }
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(notifications[position])
    }

    override fun getItemCount() = notifications.size

    class NotificationViewHolder(private val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(notification: NotificationDto) {
            binding.notification = notification
        }

        fun bindOnItemClickListener(onItemClickListener: (View, Int) -> Unit ) {
            binding.imgItemNoticeboardCancel.setOnClickListener {
                onItemClickListener(it, adapterPosition)
            }
        }
    }
}