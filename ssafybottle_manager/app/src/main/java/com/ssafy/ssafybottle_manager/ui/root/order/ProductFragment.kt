package com.ssafy.ssafybottle_manager.ui.root.order

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ssafy.ssafybottle_manager.application.MainViewModel
import com.ssafy.ssafybottle_manager.databinding.FragmentProductBinding
import com.ssafy.ssafybottle_manager.ui.adapter.ProductAdapter
import com.ssafy.ssafybottle_manager.utils.FAILURE

class ProductFragment : Fragment() {
    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel : MainViewModel by activityViewModels()
    private lateinit var productAdapter : ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        initAdapter()
        observeData()
    }

    private fun initData() {
        mainViewModel.getProducts()
    }

    private fun initAdapter() {
        productAdapter = ProductAdapter().apply {
            onItemClickListener = productItemClickListener
        }
        binding.recyclerProduct.apply {
            adapter = productAdapter
        }
    }

    private val productItemClickListener : (View, Int) -> Unit = { _, position ->

    }

    private fun observeData() {
        mainViewModel.products.observe(viewLifecycleOwner) {
            productAdapter.apply {
                products = it
                notifyDataSetChanged()
            }
        }
        mainViewModel.isProductSuccess.observe(viewLifecycleOwner) {
            when(it) {
                FAILURE -> {
                    Toast.makeText(requireContext(), "상품 정보를 가져오는데 실패했습니다.", Toast.LENGTH_SHORT).show()}
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}