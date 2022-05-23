package com.ssafy.smartstore.ui.root.card

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.Paint
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.smartstore.R
import com.ssafy.smartstore.application.MainViewModel
import com.ssafy.smartstore.application.SmartStoreApplication.Companion.isCoupon
import com.ssafy.smartstore.data.dto.card.CardDto
import com.ssafy.smartstore.databinding.FragmentCardBinding
import com.ssafy.smartstore.utils.createBarcode
import com.ssafy.smartstore.utils.getUserId
import com.ssafy.smartstore.utils.toMoney
import com.ssafy.smartstore.utils.view.getPxFromDp

class CardFragment : Fragment() {

    private var _binding: FragmentCardBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel : MainViewModel by activityViewModels()
    private val viewModel: CardViewModel by viewModels()
    private lateinit var userId: String

    private lateinit var couponDialog: AlertDialog

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
        binding.textCardHistory.paintFlags = Paint.UNDERLINE_TEXT_FLAG;

        couponDialog = makeCouponDialog()
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
        mainViewModel.coupon.observe(viewLifecycleOwner) {
            if(it > 0) {
                Toast.makeText(requireContext(), "쿠폰 ${it}원 등록이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                couponDialog.dismiss()
                mainViewModel.coupon.value = 0
                isCoupon = false
                viewModel.chargeCard(
                    CardDto(
                        content = "쿠폰등록",
                        orderId = -1,
                        payment = it,
                        userId = userId,
                        payTime = "",
                        id = 0
                    )
                )
            }
        }
        viewModel.isChargeSuccess.observe(viewLifecycleOwner) {
            if(it) {
                viewModel.checkCash(userId)
                viewModel.isChargeSuccess.value = false
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
        binding.textCardCoupon.setOnClickListener {
            isCoupon = true
            couponDialog.show()
        }
    }

    private fun makeCouponDialog() : AlertDialog {
        return AlertDialog
            .Builder(requireContext())
            .setTitle("쿠폰등록")
            .setMessage("등록할 쿠폰을 태그해주세요")
            .setNegativeButton("취소") { dialog, _ ->
                isCoupon = false
                dialog.dismiss()
            }
            .create()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}