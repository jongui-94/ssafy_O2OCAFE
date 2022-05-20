package com.ssafy.smartstore.ui.root.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.smartstore.R
import com.ssafy.smartstore.databinding.FragmentMenuDetailBinding
import com.ssafy.smartstore.ui.adapter.MenuDetailAdapter
import com.ssafy.smartstore.utils.PRODUCT_ID
import com.ssafy.smartstore.utils.retrofit.FetchState

class MenuDetailFragment : Fragment() {

    private var _binding: FragmentMenuDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MenuDetailViewModel by viewModels()

    private lateinit var beverageAdapter: MenuDetailAdapter
    private lateinit var dessertAdapter: MenuDetailAdapter
    private lateinit var top10Adapter: MenuDetailAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        initAdapter()
        registerObserver()
        setOnClickListeners()
        otherListener()
    }

    private fun initData() {
        binding.progressbarMenudetailLoading.isVisible = true
        viewModel.getProducts()
    }

    private fun initAdapter() {
        beverageAdapter = MenuDetailAdapter().apply {
            this.onItemClickListener = this@MenuDetailFragment.onItemClickListener
        }
        binding.recyclerMenudetailBeverage.apply {
            adapter = beverageAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        }

        dessertAdapter = MenuDetailAdapter().apply {
            this.onItemClickListener = this@MenuDetailFragment.onItemClickListener
        }
        binding.recyclerMenudetailDessert.apply {
            adapter = dessertAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        }

        top10Adapter = MenuDetailAdapter().apply {
            this.onItemClickListener = this@MenuDetailFragment.onItemClickListener
        }
        binding.recyclerMenudetailTop10.apply {
            adapter = top10Adapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        }
    }

    private fun registerObserver() {
        viewModel.beverage.observe(viewLifecycleOwner) {
            binding.progressbarMenudetailLoading.isVisible = false
            beverageAdapter.apply {
                products = it
                notifyDataSetChanged()
            }
        }
        viewModel.dessert.observe(viewLifecycleOwner) {
            dessertAdapter.apply {
                products = it
                notifyDataSetChanged()
            }
        }
        viewModel.top10.observe(viewLifecycleOwner) {
            top10Adapter.apply {
                products = it
                notifyDataSetChanged()
            }
        }
        viewModel.isSuccess.observe(viewLifecycleOwner) {
            if (!it) {
                Toast.makeText(requireContext(), "상품정보를 받아오는데 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.fetchState.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "상품정보를 받아오는데 실패했습니다.", Toast.LENGTH_SHORT).show()
            when (it) {
                FetchState.BAD_INTERNET -> {
                }
                FetchState.PARSE_ERROR -> {
                }
                FetchState.WRONG_CONNECTION -> {
                }
                FetchState.FAIL -> {
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.fabMenudetailCart.setOnClickListener {
            requireParentFragment().findNavController()
                .navigate(R.id.action_rootFragment_to_shoppingListFragment)
        }
        binding.imgMenudetailLocation.setOnClickListener {
            requireParentFragment().findNavController()
                .navigate(R.id.action_rootFragment_to_mapFragment)
        }
        binding.imgMenudetailSearch.setOnClickListener {
            requireParentFragment().findNavController()
                .navigate(R.id.action_rootFragment_to_searchFragment)
        }
    }

    private fun otherListener() {
        binding.refreshMenudetail.setOnRefreshListener {
            initData()
            binding.refreshMenudetail.isRefreshing = false
        }
    }

    private val onItemClickListener: (View, Int) -> Unit = { _, id ->
        requireParentFragment().findNavController().navigate(
            R.id.action_rootFragment_to_orderFragment,
            bundleOf(PRODUCT_ID to id)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}