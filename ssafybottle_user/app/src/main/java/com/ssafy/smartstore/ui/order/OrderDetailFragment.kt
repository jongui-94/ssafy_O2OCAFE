package com.ssafy.smartstore.ui.order

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.smartstore.application.MainViewModel
import com.ssafy.smartstore.application.SmartStoreApplication.Companion.isCoupon
import com.ssafy.smartstore.databinding.FragmentOrderDetailBinding
import com.ssafy.smartstore.ui.adapter.OrderDetailAdapter
import com.ssafy.smartstore.utils.ORDER_ID
import com.ssafy.smartstore.utils.toDateString
import com.ssafy.smartstore.utils.toMoney
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class OrderDetailFragment : Fragment() {

    private var _binding: FragmentOrderDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: OrderDetailViewModel by viewModels()

    private lateinit var adapter: OrderDetailAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        initAdapter()
        registerObserver()
        setOnClickListeners()
    }

    private fun initData() {
        val orderId = requireArguments().getInt(ORDER_ID)
        viewModel.getOrderDetails(orderId)
    }

    private fun initAdapter() {
        adapter = OrderDetailAdapter()
        binding.recyclerOrderdetailOrder.apply {
            this.adapter = this@OrderDetailFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun registerObserver() {
        viewModel.orderDetailProducts.observe(viewLifecycleOwner) {
            adapter.apply {
                orderDetails = it
                notifyDataSetChanged()
            }

            var totalCost = 0
            it.forEach { orderDetail ->
                totalCost += orderDetail.totalprice
            }
            binding.textOrderdetailTotalprice.text = "${toMoney(totalCost)}원"
            binding.textOrderdetailDate.text = toDateString(it[0].order_time)
        }
    }

    private fun setOnClickListeners() {
        binding.imgOrderdetailBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}