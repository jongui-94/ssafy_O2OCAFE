package com.ssafy.smartstore.ui.root.card

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ssafy.smartstore.data.dto.card.CardDto
import com.ssafy.smartstore.databinding.FragmentCardChargeBinding
import com.ssafy.smartstore.utils.dummyChargePrices
import com.ssafy.smartstore.utils.getUserId

class CardChargeFragment : Fragment() {

    private var _binding: FragmentCardChargeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CardChargeViewModel by viewModels()
    private lateinit var userId: String

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
        initData()
        observeData()
        setOnClickListeners()
    }

    private fun initViews() {
        binding.numberpickerCardcharge.apply {
            minValue = 0
            maxValue = dummyChargePrices.size - 1
            displayedValues = dummyChargePrices
        }
    }

    private fun initData() {
        userId = getUserId()
    }

    private fun observeData() {
        viewModel.isSuccess.observe(viewLifecycleOwner) {
            binding.progressbarCardchargeLoading.isVisible = false
            if(it) {
                Toast.makeText(requireContext(), "카드 충전이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                requireActivity().onBackPressed()
            } else {
                Toast.makeText(requireContext(), "카드 충전에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setOnClickListeners() {
        binding.imgCardchargeBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.btnCardchargeCharge.setOnClickListener {
            binding.progressbarCardchargeLoading.isVisible = true
            viewModel.chargeCard(
                CardDto(
                    content = "카드충전",
                    orderId = -1,
                    payment = (binding.numberpickerCardcharge.value + 1) * 10000,
                    userId = userId,
                    payTime = "",
                    id = 0
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}