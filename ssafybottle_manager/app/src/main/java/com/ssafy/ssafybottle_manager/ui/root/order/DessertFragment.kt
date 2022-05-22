package com.ssafy.ssafybottle_manager.ui.root.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ssafy.ssafybottle_manager.application.MainViewModel
import com.ssafy.ssafybottle_manager.data.dto.order.OrderDetailDto
import com.ssafy.ssafybottle_manager.databinding.FragmentDessertBinding
import com.ssafy.ssafybottle_manager.ui.adapter.ProductAdapter
import com.ssafy.ssafybottle_manager.utils.FAILURE

class DessertFragment : Fragment() {
    private var _binding: FragmentDessertBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var dessertAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDessertBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        initAdapter()
        observeData()
    }


    private fun initData() {
        mainViewModel.getDessert()
    }

    private fun initAdapter() {
        dessertAdapter = ProductAdapter().apply {
            onItemClickListener = productItemClickListener
        }
        binding.recyclerDessert.apply {
            adapter = dessertAdapter
        }
    }

    private val productItemClickListener: (View, Int) -> Unit = { _, position ->
        // 선택한 상품
        val product = mainViewModel.dessert.value!![position]

        var alreadyIn = false

        // 주문서에 이미 해당 상품이 있다면
        mainViewModel.orderList.forEach {
            if(it.productId == product.id) {
                // 상품 한 개 더하기
                it.quantity++
                mainViewModel.liveOrderList.value = mainViewModel.orderList
                alreadyIn = true
                return@forEach
            }
        }

        // 주문서에 새로 추가하는 경우
        if(!alreadyIn) {
            mainViewModel.orderList.add(
                OrderDetailDto(
                    productId = product.id,
                    quantity = 1,
                    img = product.img,
                    name = product.name,
                    price = product.price
                )
            )
        }
        mainViewModel.liveOrderList.value = mainViewModel.orderList
    }

    private fun observeData() {
        mainViewModel.dessert.observe(viewLifecycleOwner) {
            dessertAdapter.apply {
                products = it
                notifyDataSetChanged()
            }
        }
        mainViewModel.isDessertSuccess.observe(viewLifecycleOwner) {
            when (it) {
                FAILURE -> {
                    Toast.makeText(requireContext(), "상품 정보를 가져오는데 실패했습니다.", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}