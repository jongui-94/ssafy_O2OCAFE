package com.ssafy.ssafybottle_manager.ui.root.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ssafy.ssafybottle_manager.application.MainViewModel
import com.ssafy.ssafybottle_manager.databinding.FragmentBeverageBinding
import com.ssafy.ssafybottle_manager.ui.adapter.ProductAdapter
import com.ssafy.ssafybottle_manager.utils.FAILURE

class BeverageFragment : Fragment() {
    private var _binding: FragmentBeverageBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel : MainViewModel by activityViewModels()
    private lateinit var beverageAdapter : ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBeverageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        initAdapter()
        observeData()
    }

    private fun initData() {
        mainViewModel.getBeverage()
    }

    private fun initAdapter() {
        beverageAdapter = ProductAdapter().apply {
            onItemClickListener = productItemClickListener
        }
        binding.recyclerBeverage.apply {
            adapter = beverageAdapter
        }
    }

    private val productItemClickListener : (View, Int) -> Unit = { _, position ->

    }

    private fun observeData() {
        mainViewModel.beverage.observe(viewLifecycleOwner) {
            beverageAdapter.apply {
                products = it
                notifyDataSetChanged()
            }
        }
        mainViewModel.isBeverageSuccess.observe(viewLifecycleOwner) {
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