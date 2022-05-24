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
import com.ssafy.smartstore.R
import com.ssafy.smartstore.data.dto.card.CardDto
import com.ssafy.smartstore.databinding.FragmentCardChargeBinding
import com.ssafy.smartstore.utils.BOOTPAY_APPLICATION_ID
import com.ssafy.smartstore.utils.dummyChargePrices
import com.ssafy.smartstore.utils.getUserId
import com.ssafy.smartstore.utils.toMoney
import kr.co.bootpay.Bootpay
import kr.co.bootpay.enums.Method
import kr.co.bootpay.enums.PG
import kr.co.bootpay.enums.UX
import kr.co.bootpay.model.BootExtra
import kr.co.bootpay.model.BootUser

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

        initData()
        observeData()
        setOnClickListeners()
    }

    private fun initData() {
        userId = getUserId()
    }

    private fun observeData() {
        viewModel.isSuccess.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(requireContext(), "카드 충전이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                requireActivity().onBackPressed()
            } else {
                Toast.makeText(requireContext(), "카드 충전에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.price.observe(viewLifecycleOwner) {
            binding.textChadchargePrice.text = "${toMoney(it)}원"
        }
    }

    private fun setOnClickListeners() {
        binding.imgCardchargeBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.btnCardchargeCharge.setOnClickListener {
            requestBootPay()
        }
        binding.radiogroupCardchargeMoney.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbt_cardcharge_5000 -> {
                    viewModel.price.value = 5000
                }
                R.id.rbt_cardcharge_10000 -> {
                    viewModel.price.value = 10000
                }
                R.id.rbt_cardcharge_30000 -> {
                    viewModel.price.value = 30000
                }
                R.id.rbt_cardcharge_50000 -> {
                    viewModel.price.value = 50000
                }
                R.id.rbt_cardcharge_100000 -> {
                    viewModel.price.value = 100000
                }
            }
        }
    }

    private fun requestBootPay() {
        val bootUser = BootUser().setPhone("010-1234-5678")
        val bootExtra = BootExtra().setQuotas(intArrayOf(0, 2, 3))

        Bootpay.init(parentFragmentManager)
            .setApplicationId(BOOTPAY_APPLICATION_ID)
            .setPG(PG.KAKAO)
            .setMethod(Method.EASY)
            .setContext(requireContext())
            .setBootUser(bootUser)
            .setBootExtra(bootExtra)
            .setName("싸피보틀 카드 충전")// 결제할 상품명
            .setOrderId("1234") // 결제 고유번호expire_month
            .setPrice(viewModel.price.value!!) // 결제할 금액
            .addItem("카드충전", 1, "ITEM_CODE_CHARGE_CARD", 1000) // 주문정보에 담길 상품정보, 통계를 위해 사용
            .onConfirm { message ->
                //if (0 < stuck) Bootpay.confirm(message); // 재고가 있을 경우.
                //else Bootpay.removePaymentWindow(); // 재고가 없어 중간에 결제창을 닫고 싶을 경우
                Bootpay.confirm(message)
                Log.d("confirm_싸피", message);
            }
            .onDone { message ->
                Log.d("done_싸피", message)
                viewModel.chargeCard(
                    CardDto(
                        content = "카드충전",
                        orderId = -1,
                        payment = viewModel.price.value!!,
                        userId = userId,
                        payTime = "",
                        id = 0
                    )
                )
            }
            .onReady { message ->
                Log.d("ready", message)
            }
            .onCancel { message ->
                Log.d("cancel", message)
                Toast.makeText(requireContext(), "결제가 취소되었습니다.", Toast.LENGTH_SHORT).show()
            }
            .onError { message ->
                Log.d("error", message)
            }
            .onClose { message ->
                Log.d("close", message)
            }
            .request();
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}