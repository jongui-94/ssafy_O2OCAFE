package com.ssafy.ssafybottle_manager.ui.root.order_management

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ssafy.ssafybottle_manager.application.MainViewModel
import com.ssafy.ssafybottle_manager.databinding.FragmentOrderManagementBinding
import com.ssafy.ssafybottle_manager.ui.adapter.OrderManagementAdapter

class OrderManagementFragment : Fragment() {
    private var _binding: FragmentOrderManagementBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel : MainViewModel by activityViewModels()
    private lateinit var orderManagementAdapter: OrderManagementAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderManagementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        initAdapter()
        observeData()
        otherListeners()
    }

    private fun initData() {
        mainViewModel.getOrderList()
    }

    private fun initAdapter() {
        orderManagementAdapter = OrderManagementAdapter().apply {
            onItemClickListener = orderClickListener
        }
        binding.recyclerOrdermanagement.apply {
            adapter = orderManagementAdapter
        }
    }

    private val orderClickListener: (View, Int) -> Unit = { _, position ->

    }

    private fun observeData() {
        mainViewModel.orders.observe(viewLifecycleOwner) {
            orderManagementAdapter.apply {
                orders = it
                notifyDataSetChanged()
            }
        }
    }

    private fun otherListeners() {
        binding.refreshOrdermanagement.setOnRefreshListener {
            initData()
            binding.refreshOrdermanagement.isRefreshing = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}