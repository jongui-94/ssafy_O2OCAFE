package com.ssafy.smartstore.ui.root.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.smartstore.R
import com.ssafy.smartstore.databinding.FragmentSearchBinding
import com.ssafy.smartstore.ui.adapter.SearchAdapter
import com.ssafy.smartstore.utils.PRODUCT_ID

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        observeData()
        setOnClickListeners()
    }

    private fun initAdapter() {
        searchAdapter = SearchAdapter().apply {
            this.onItemClickListener = this@SearchFragment.onItemClickListener
        }
        binding.recyclerSearch.apply {
            adapter = searchAdapter
        }
    }

    private val onItemClickListener: (View, Int) -> Unit = { _, id ->
        findNavController().navigate(
            R.id.action_searchFragment_to_orderFragment,
            bundleOf(PRODUCT_ID to id)
        )
    }

    private fun observeData() {
        viewModel.products.observe(viewLifecycleOwner) {
            binding.progressbarSearchLoading.isVisible = false
            binding.nocontentSearch.isVisible = it.isEmpty()
            searchAdapter.apply {
                products = it
                notifyDataSetChanged()
            }
        }
        viewModel.isSuccess.observe(viewLifecycleOwner) {
            binding.progressbarSearchLoading.isVisible = false
            if (!it) {
                Toast.makeText(requireContext(), "상품 검색을 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setOnClickListeners() {
        binding.imgSearchBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.edtSearchSearch.setOnEditorActionListener { v, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    (v as EditText).text.toString().let {
                        if (it.isBlank()) {
                            Toast.makeText(requireContext(), "검색어를 입력해주세요", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            binding.progressbarSearchLoading.isVisible = true
                            viewModel.searchProduct(it)
                        }
                    }
                    return@setOnEditorActionListener true
                }
                else -> return@setOnEditorActionListener false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}