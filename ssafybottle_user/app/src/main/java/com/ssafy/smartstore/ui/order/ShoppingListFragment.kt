package com.ssafy.smartstore.ui.order

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.smartstore.R
import com.ssafy.smartstore.application.MainViewModel
import com.ssafy.smartstore.application.SmartStoreApplication.Companion.tableName
import com.ssafy.smartstore.data.dto.order.OrderRequestDto
import com.ssafy.smartstore.databinding.FragmentShoppingListBinding
import com.ssafy.smartstore.ui.adapter.OnItemClickListener
import com.ssafy.smartstore.ui.adapter.ShoppingListAdapter
import com.ssafy.smartstore.utils.getUserId
import com.ssafy.smartstore.utils.toMoney

class ShoppingListFragment : Fragment() {

    private var _binding: FragmentShoppingListBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by activityViewModels()
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
        initViews()
        initAdapter()
        registerObserver()
        setOnClickListeners()
    }

    private fun initData() {
        userId = getUserId()
        mainViewModel.checkCash(userId)
    }

    private fun initViews() {
        var totalQuantity = 0
        var totalPrice = 0
        mainViewModel.orderList.forEach {
            totalQuantity += it.quantity
            totalPrice += (it.price * it.quantity)
        }

        binding.apply {
            textShoppinglistTotalquantity.text = "${totalQuantity}개"
            textShoppinglistTotalprice.text = "${toMoney(totalPrice)}원"
        }
    }

    private fun initAdapter() {
        adapter = ShoppingListAdapter().apply {
            shoppingList = mainViewModel.orderList
            itemClickListener = onItemClickListener
        }
        binding.recyclerShoppinglistOrder.apply {
            this.adapter = this@ShoppingListFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private val onItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, position: Int) {
            mainViewModel.orderList.removeAt(position)
            adapter.apply {
                shoppingList = mainViewModel.orderList
                notifyDataSetChanged()
            }
            initViews()
        }
    }

    private fun registerObserver() {
        mainViewModel.isComplete.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(requireContext(), "주문을 완료하였습니다.", Toast.LENGTH_SHORT).show()
                mainViewModel.orderList = mutableListOf()
                tableName = ""
                mainViewModel.isComplete = MutableLiveData()
                requireActivity().onBackPressed()
            } else {
                Toast.makeText(requireContext(), "주문에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
        mainViewModel.lackOfBalance.observe(viewLifecycleOwner) {
            if(it) {
                Toast.makeText(requireContext(), "카드 잔액이 부족합니다.", Toast.LENGTH_SHORT).show()
                mainViewModel.lackOfBalance.value = false
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
            if (mainViewModel.orderList.isEmpty()) {
                Toast.makeText(requireContext(), "장바구니에 담긴 상품이 없습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (isStore) {
                if (tableName == "") {
                    showNfcDialog()
                    return@setOnClickListener
                }
            }

            mainViewModel.postOrder(
                OrderRequestDto(
                    details = mainViewModel.orderList,
                    orderTable = tableName,
                    userId = userId
                )
            )
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
        binding.textShoppinglistRemoveall.setOnClickListener {
            initCart()
        }
    }

    private fun initCart() {
        mainViewModel.orderList = mutableListOf()
        adapter.apply {
            shoppingList = mainViewModel.orderList
            notifyDataSetChanged()
        }
        binding.apply {
            textShoppinglistTotalquantity.text = "0개"
            textShoppinglistTotalprice.text = "0원"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}