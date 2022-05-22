package com.ssafy.ssafybottle_manager.ui.root.order

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.ssafy.ssafybottle_manager.R
import com.ssafy.ssafybottle_manager.application.MainViewModel
import com.ssafy.ssafybottle_manager.databinding.FragmentOrderBinding
import com.ssafy.ssafybottle_manager.databinding.FragmentOrderManagementBinding
import com.ssafy.ssafybottle_manager.ui.adapter.OrderViewPagerAdapter
import com.ssafy.ssafybottle_manager.utils.DEFAULT
import com.ssafy.ssafybottle_manager.utils.FAILURE

class OrderFragment : Fragment() {
    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel : MainViewModel by activityViewModels()

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

        initData()
        initView()
        observeData()
    }

    private fun initData() {
    }

    private fun initView() {
        binding.viewpager2Order.adapter = OrderViewPagerAdapter(childFragmentManager, lifecycle)

        TabLayoutMediator(binding.tabOrder, binding.viewpager2Order) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()
    }


    private fun observeData() {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}