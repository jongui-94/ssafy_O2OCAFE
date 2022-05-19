package com.ssafy.smartstore.ui.root.card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ssafy.smartstore.databinding.FragmentCardChargeBinding
import com.ssafy.smartstore.utils.dummyChargePrices

class CardChargeFragment : Fragment() {

    private var _binding : FragmentCardChargeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCardChargeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setOnClickListeners()
    }

    private fun initViews() {
        binding.numberpickerCardcharge.apply {
            minValue = 0
            maxValue = dummyChargePrices.size - 1
            displayedValues = dummyChargePrices
        }
    }

    private fun setOnClickListeners() {
        binding.imgCardchargeBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}