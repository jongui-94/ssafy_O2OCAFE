package com.ssafy.ssafybottle_manager.ui.root.order_management

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ssafy.ssafybottle_manager.R
import com.ssafy.ssafybottle_manager.application.MainViewModel
import com.ssafy.ssafybottle_manager.databinding.FragmentOrderManagementBinding
import com.ssafy.ssafybottle_manager.ui.adapter.OrderDetailAdapter
import com.ssafy.ssafybottle_manager.ui.adapter.OrderManagementAdapter
import com.ssafy.ssafybottle_manager.utils.toMoney
import java.lang.Appendable

class OrderManagementFragment : Fragment() {
    private var _binding: FragmentOrderManagementBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var orderManagementAdapter: OrderManagementAdapter
    private lateinit var orderDetailAdapter: OrderDetailAdapter

    private var orderId = -1
    private var completed = "Y"

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
        setOnClickListeners()
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

        orderDetailAdapter = OrderDetailAdapter()
        binding.recyclerOrderdetail.apply {
            adapter = orderDetailAdapter
        }
    }

    private val orderClickListener: (View, Int) -> Unit = { _, position ->
        mainViewModel.changeOrder(position)
        orderManagementAdapter.notifyItemChanged(mainViewModel.prevItem)
        orderManagementAdapter.notifyItemChanged(position)
        mainViewModel.prevItem = position

        mainViewModel.getOrder(mainViewModel.liveOrders.value!![position].orderId)
        orderId = mainViewModel.liveOrders.value!![position].orderId
        completed = mainViewModel.liveOrders.value!![position].completed
    }

    private fun observeData() {
        mainViewModel.liveOrders.observe(viewLifecycleOwner) {
            orderManagementAdapter.apply {
                orders = it
                notifyDataSetChanged()
            }
        }
        mainViewModel.orderDetail.observe(viewLifecycleOwner) {
            var totalCost = 0
            it.forEach { order ->
                totalCost += order.totalprice
            }

            orderDetailAdapter.apply {
                orderDetails = it
                notifyDataSetChanged()
            }
            binding.textOrderdetailTotalcost.text = "${toMoney(totalCost)}원"
            if (it[0].completed == "Y") {
                binding.btnOrdermanagementOrderdetail.apply {
                    isEnabled = false
                    setBackgroundColor(Color.parseColor("#E0E0E0"))
                    background = resources.getDrawable(R.drawable.button_regular)
                }
            } else {
                binding.btnOrdermanagementOrderdetail.apply {
                    isEnabled = true
                    setBackgroundColor(Color.parseColor("#00a1dd"))
                    background = resources.getDrawable(R.drawable.button_regular)
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.btnOrdermanagementOrderdetail.setOnClickListener {
            if (orderId != -1 && completed == "N") {
                // todo 상품 준비 완료 처리

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