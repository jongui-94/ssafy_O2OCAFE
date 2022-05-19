package com.ssafy.smartstore.ui.root.mypage

import android.app.AlertDialog
import android.content.DialogInterface
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
import com.ssafy.smartstore.data.dto.user.UserInfoResponseDto
import com.ssafy.smartstore.databinding.FragmentMyPageBinding
import com.ssafy.smartstore.ui.adapter.OnItemClickListener
import com.ssafy.smartstore.ui.adapter.OrderHistoryAdapter
import com.ssafy.smartstore.utils.*
import com.ssafy.smartstore.utils.view.getResourceId

class MyPageFragment : Fragment() {

    private var _binding: FragmentMyPageBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MyPageViewModel by viewModels()
    private lateinit var adapter: OrderHistoryAdapter

    private lateinit var userId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        registerObserver()
        setOnClickListeners()
        otherListener()
    }

    override fun onResume() {
        super.onResume()
        initData()
    }

    private fun initData() {
        binding.progressbarMypageLoading.isVisible = true
        userId = getUserId()
        viewModel.getUser(userId)
        viewModel.getRecentOrders(userId)
    }

    private fun initAdapter() {
        adapter = OrderHistoryAdapter().apply {
            onItemClickListener = itemClickListener
        }
        binding.recyclerMypageOrderhistory.apply {
            this.adapter = this@MyPageFragment.adapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        }
    }

    private fun registerObserver() {
        viewModel.isSuccess.observe(viewLifecycleOwner) {
            if(!it) {
                Toast.makeText(requireContext(), "유저 정보를 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.user.observe(viewLifecycleOwner) {
            setUserInfo(it)
        }
        viewModel.orderByUserId.observe(viewLifecycleOwner) {
            binding.progressbarMypageLoading.isVisible = false
            binding.nocontentMypageRecentorder.isVisible = it.isEmpty()

            adapter.apply {
                orderHistories = it
                notifyDataSetChanged()
            }
        }
    }

    private fun setUserInfo(user: UserInfoResponseDto) {
        binding.apply {
            textMypageName.text = user.user.name
            textMypageSeedsTitle.text = "${user.grade.title} ${user.grade.step}단계"
            textMypageSeedsCount1.text = "${user.user.stamps}"
            textMypageSeedsCount2.text = "${user.grade.to + user.user.stamps}"
            textMypageSeedsDescription.text = "다음 레벨까지 ${user.grade.to}잔 남았습니다"
            progressbarMypageSeeds.max = user.grade.to + user.user.stamps
            progressbarMypageSeeds.progress = user.user.stamps
            imgMypageSeeds.setImageResource(requireView().getResourceId(user.grade.img))
        }
    }

    private val itemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, position: Int) {
            requireParentFragment().findNavController().navigate(
                R.id.action_rootFragment_to_orderDetailFragment,
                bundleOf(ORDER_ID to viewModel.orderByUserId.value!![position].o_id)
            )
        }
    }

    private fun setOnClickListeners() {
        binding.textMypageLogout.setOnClickListener {
            showLogoutDialog { dialog, _ ->
                unSetAutoLogin()
                clearUserId()
                findNavController().navigate(R.id.action_rootFragment_to_loginMainFragment)
                dialog.dismiss()
            }
        }
    }

    private fun otherListener() {
        binding.refreshMypage.setOnRefreshListener {
            initData()
            binding.refreshMypage.isRefreshing = false
        }
    }

    private fun showLogoutDialog(listener: DialogInterface.OnClickListener) {
        AlertDialog
            .Builder(requireContext())
            .setTitle("로그아웃")
            .setMessage("정말로 로그아웃 하시겠습니까?")
            .setPositiveButton("확인", listener)
            .setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}