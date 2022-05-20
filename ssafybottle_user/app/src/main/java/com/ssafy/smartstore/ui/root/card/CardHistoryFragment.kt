package com.ssafy.smartstore.ui.root.card

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.smartstore.R
import com.ssafy.smartstore.databinding.FragmentCardHistoryBinding
import com.ssafy.smartstore.ui.adapter.CardHistoryAdapter
import com.ssafy.smartstore.utils.getUserId

class CardHistoryFragment : Fragment() {

    private var _binding : FragmentCardHistoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel : CardHistoryViewModel by viewModels()
    private lateinit var cardHistoryAdapter: CardHistoryAdapter

    private lateinit var userId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCardHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        initAdapter()
        observeData()
        setOnClickListeners()
    }

    private fun initData() {
        binding.progressbarCardhistoryLoading.isVisible = true
        userId = getUserId()
        viewModel.getCardHistory(userId)
    }

    private fun initAdapter() {
        cardHistoryAdapter = CardHistoryAdapter()
        binding.recyclerCardhistory.apply {
            adapter = cardHistoryAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeData() {
        viewModel.cardList.observe(viewLifecycleOwner) {
            binding.progressbarCardhistoryLoading.isVisible = false
            binding.nocontentCardhistory.isVisible = it.isEmpty()

            if(it.isNotEmpty()) {
                cardHistoryAdapter.apply {
                    cardList = it
                    notifyDataSetChanged()
                }
            }
        }

        viewModel.isSuccess.observe(viewLifecycleOwner) {
            if(!it) {
                binding.progressbarCardhistoryLoading.isVisible = false
                Toast.makeText(requireContext(), "카드 사용내역을 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setOnClickListeners() {
        binding.imgCardhistoryBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}