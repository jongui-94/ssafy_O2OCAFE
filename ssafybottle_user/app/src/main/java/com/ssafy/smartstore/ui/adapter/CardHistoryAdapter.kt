package com.ssafy.smartstore.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.smartstore.data.dto.card.CardHistory
import com.ssafy.smartstore.databinding.ItemCardHistoryBinding
import com.ssafy.smartstore.utils.dummyCardHistories

class CardHistoryAdapter : RecyclerView.Adapter<CardHistoryAdapter.CardHistoryViewHolder>() {

    var cardHistories: List<CardHistory> = dummyCardHistories

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
        holder.bind(cardHistories[position])
    }

    override fun getItemCount() = cardHistories.size

    class CardHistoryViewHolder(private val binding: ItemCardHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cardHistory: CardHistory) {
            binding.cardHistory = cardHistory
        }
    }
}