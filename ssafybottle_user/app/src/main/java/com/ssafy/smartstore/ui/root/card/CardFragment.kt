package com.ssafy.smartstore.ui.root.card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.smartstore.databinding.FragmentCardBinding
import com.ssafy.smartstore.ui.adapter.CardHistoryAdapter

class CardFragment : Fragment() {

    private var _binding : FragmentCardBinding? = null
    private val binding get() = _binding!!

    private lateinit var cardHistoryAdapter: CardHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        setOnClickListeners()
    }

    private fun initAdapter() {
        cardHistoryAdapter = CardHistoryAdapter()
        binding.recyclerCardHistory.apply {
            adapter = cardHistoryAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setOnClickListeners() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}