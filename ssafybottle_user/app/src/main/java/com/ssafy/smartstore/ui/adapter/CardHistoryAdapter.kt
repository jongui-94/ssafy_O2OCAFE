package com.ssafy.smartstore.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.smartstore.data.dto.card.CardDto
import com.ssafy.smartstore.databinding.ItemCardHistoryBinding

class CardHistoryAdapter : RecyclerView.Adapter<CardHistoryAdapter.CardHistoryViewHolder>() {

    var cardList: List<CardDto> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHistoryViewHolder {
        return CardHistoryViewHolder(
            ItemCardHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CardHistoryViewHolder, position: Int) {
        holder.bind(cardList[position])
    }

    override fun getItemCount() = cardList.size

    class CardHistoryViewHolder(private val binding: ItemCardHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cardDto: CardDto) {
            binding.card = cardDto
        }
    }
}