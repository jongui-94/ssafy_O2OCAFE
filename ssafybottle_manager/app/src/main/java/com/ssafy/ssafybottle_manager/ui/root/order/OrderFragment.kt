package com.ssafy.ssafybottle_manager.ui.root.order

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.ssafy.ssafybottle_manager.application.MainActivity
import com.ssafy.ssafybottle_manager.application.MainViewModel
import com.ssafy.ssafybottle_manager.databinding.FragmentOrderBinding
import com.ssafy.ssafybottle_manager.ui.adapter.BillAdapter
import com.ssafy.ssafybottle_manager.ui.adapter.OrderViewPagerAdapter
import com.ssafy.ssafybottle_manager.utils.toMoney

class OrderFragment : Fragment() {
    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var billAdapter: BillAdapter

    private val tabTitle = arrayOf("전체", "음료", "디저트")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initView()
        observeData()
        setOnClickListeners()
    }

    private fun initAdapter() {
        billAdapter = BillAdapter().apply {
            onItemClickListener = billItemClickListener
            onItemChangeListener = billItemChangeListener
        }
        binding.recyclerBill.apply {
            adapter = billAdapter
        }
    }

    private val billItemClickListener: (View, Int) -> Unit = { _, position ->
        mainViewModel.orderList.removeAt(position)
        mainViewModel.liveOrderList.value = mainViewModel.orderList
    }

    private val billItemChangeListener: (View, Int, Int) -> Unit = { _, position, quantity ->
        mainViewModel.orderList[position].quantity = quantity
        mainViewModel.liveOrderList.value = mainViewModel.orderList
    }

    private fun initView() {
        binding.viewpager2Order.adapter = OrderViewPagerAdapter(childFragmentManager, lifecycle)

        TabLayoutMediator(binding.tabOrder, binding.viewpager2Order) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()
    }

    private fun observeData() {
        mainViewModel.liveOrderList.observe(viewLifecycleOwner) {
            billAdapter.apply {
                orderList = it
                notifyDataSetChanged()
            }

            var sum = 0
            it.forEach { detail ->
                sum += detail.price * detail.quantity
            }
            mainViewModel.totalCost.value = sum
        }
        mainViewModel.totalCost.observe(viewLifecycleOwner) {
            binding.textOrderTotalcost.text = "${toMoney(it)}원"
        }
    }

    private fun setOnClickListeners() {
        binding.textOrderRemoveall.setOnClickListener {
            mainViewModel.orderList = mutableListOf()
            mainViewModel.liveOrderList.value = mainViewModel.orderList
        }
        binding.btnOrderOrder.setOnClickListener {
            (requireActivity() as MainActivity).startBarcodeReader()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}