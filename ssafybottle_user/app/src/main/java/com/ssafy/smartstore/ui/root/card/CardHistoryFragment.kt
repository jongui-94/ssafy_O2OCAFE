package com.ssafy.smartstore.ui.root.card

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.smartstore.R
import com.ssafy.smartstore.databinding.FragmentCardHistoryBinding
import com.ssafy.smartstore.ui.adapter.CardHistoryAdapter

class CardHistoryFragment : Fragment() {

    private var _binding : FragmentCardHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var cardHistoryAdapter: CardHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCardHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        setOnClickListeners()
    }

    private fun initAdapter() {
        cardHistoryAdapter = CardHistoryAdapter()
        binding.recyclerCardhistory.apply {
            adapter = cardHistoryAdapter
            layoutManager = LinearLayoutManager(requireContext())
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