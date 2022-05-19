package com.ssafy.smartstore.ui.order

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.smartstore.R
import com.ssafy.smartstore.application.SmartStoreApplication.Companion.isOrder
import com.ssafy.smartstore.application.SmartStoreApplication.Companion.orderId
import com.ssafy.smartstore.application.SmartStoreApplication.Companion.tableName
import com.ssafy.smartstore.databinding.FragmentShoppingListBinding
import com.ssafy.smartstore.ui.adapter.ShoppingListAdapter
import com.ssafy.smartstore.utils.getUserId

class ShoppingListFragment : Fragment() {

    private var _binding: FragmentShoppingListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ShoppingListViewModel by viewModels()
    private lateinit var adapter: ShoppingListAdapter

    private lateinit var userId: String
    private var isStore = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShoppingListBinding.inflate(inflater, container, false)
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
        userId = getUserId()
        if (isOrder) {
            viewModel.getShoppingList(orderId)
        }
    }

    private fun initAdapter() {
        adapter = ShoppingListAdapter()
        binding.recyclerShoppinglistOrder.apply {
            this.adapter = this@ShoppingListFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun registerObserver() {
        viewModel.shoppingList.observe(viewLifecycleOwner) {
            adapter.apply {
                shoppingList = it
                notifyDataSetChanged()
            }

            var totalCost = 0
            var totalCount = 0
            it.forEach { item ->
                totalCost += item.totalPrice
                totalCount += item.quantity
            }

            binding.apply {
                textShoppinglistTotalprice.text = "$totalCost 원"
                textShoppinglistTotalquantity.text = "총 ${totalCount}개"
            }
        }

        viewModel.isComplete.observe(viewLifecycleOwner) {
            if (it) {
                isOrder = false
                orderId = 0
                Toast.makeText(requireContext(), "주문을 완료하였습니다.", Toast.LENGTH_SHORT).show()
                requireActivity().onBackPressed()
            } else {
                Toast.makeText(requireContext(), "주문에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showNfcDialog() {
        AlertDialog
            .Builder(requireContext())
            .setTitle("알림")
            .setMessage("TableNFC를 먼저 찍어주세요.")
            .setPositiveButton("확인") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun setOnClickListeners() {
        binding.btnShoppinglistOrder.setOnClickListener {
            if (isOrder) {
                if (isStore) {
                    if(tableName == "") {
                        showNfcDialog()
                        return@setOnClickListener
                    }
                }
                viewModel.completeOrder(orderId)
            } else {
                Toast.makeText(requireContext(), "장바구니에 담긴 상품이 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }
        binding.imgShoppinglistBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.btnShoppinglistStore.setOnClickListener {
            isStore = true
            binding.apply {
                btnShoppinglistStore.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.primaryColor
                    )
                )
                btnShoppinglistTout.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.gray
                    )
                )
            }
        }
        binding.btnShoppinglistTout.setOnClickListener {
            isStore = false
            binding.apply {
                btnShoppinglistStore.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.gray
                    )
                )
                btnShoppinglistTout.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.primaryColor
                    )
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}