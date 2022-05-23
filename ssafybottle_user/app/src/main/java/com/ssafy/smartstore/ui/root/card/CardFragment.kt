package com.ssafy.smartstore.ui.root.card

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.smartstore.R
import com.ssafy.smartstore.databinding.FragmentCardBinding
import com.ssafy.smartstore.utils.createBarcode
import com.ssafy.smartstore.utils.getUserId
import com.ssafy.smartstore.utils.toMoney
import com.ssafy.smartstore.utils.view.getPxFromDp

class CardFragment : Fragment() {

    private var _binding: FragmentCardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CardViewModel by viewModels()
    private lateinit var userId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        initView()
        observeData()
        setOnClickListeners()
    }

    private fun initData() {
        userId = getUserId()
        viewModel.checkCash(userId)
    }

    private fun initView() {
        val barcode = Bitmap.createScaledBitmap(
            createBarcode(userId),
            requireContext().getPxFromDp(340f),
            requireContext().getPxFromDp(56f),
            true
        )
        binding.imgCardBarcode.setImageBitmap(barcode)
    }

    private fun observeData() {
        viewModel.cash.observe(viewLifecycleOwner) {
            binding.textCardCash.text = "${toMoney(it)}원"
        }
        viewModel.isSuccess.observe(viewLifecycleOwner) {
            if (!it) {
                Toast.makeText(requireContext(), "카드 잔액 조회를 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setOnClickListeners() {
        binding.textCardHistory.setOnClickListener {
            requireParentFragment().findNavController()
                .navigate(R.id.action_rootFragment_to_cardHistoryFragment)
        }
        binding.textCardCharge.setOnClickListener {
            requireParentFragment().findNavController()
                .navigate(R.id.action_rootFragment_to_cardChargeFragment)
        }
        binding.imgCardRefresh.setOnClickListener {
            viewModel.checkCash(userId)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}