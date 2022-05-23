package com.ssafy.ssafybottle_manager.ui.root.sales

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.ssafy.ssafybottle_manager.R
import com.ssafy.ssafybottle_manager.application.MainViewModel
import com.ssafy.ssafybottle_manager.databinding.FragmentProductDetailBinding

class ProductDetailFragment : Fragment() {

    private var _binding : FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTopLayoutTouch()
        observeData()
        setOnClickListeners()
    }

    fun getProductDetail(productId: Int) {
        mainViewModel.getProductDetail(productId)
    }

    private fun observeData() {
        mainViewModel.productDetail.observe(viewLifecycleOwner) {
            Log.d("DetailFragment_싸피", it.toString())
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setTopLayoutTouch() {
        binding.root.setOnTouchListener { _, _ ->
            true
        }
    }

    private fun setOnClickListeners() {
        binding.imgProductdetailClose.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                hide(this@ProductDetailFragment)
                commit()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}