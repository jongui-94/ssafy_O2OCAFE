package com.ssafy.smartstore.ui.root.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.smartstore.databinding.FragmentNotificationBinding
import com.ssafy.smartstore.ui.adapter.NoticeBoardAdapter
import com.ssafy.smartstore.ui.adapter.OnItemClickListener
import com.ssafy.smartstore.utils.getUserId

class NotificationFragment : Fragment() {

    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NotificationViewModel by viewModels()
    private lateinit var noticeBoardAdapter: NoticeBoardAdapter
    private lateinit var userId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userId = getUserId()
        initData()
        initAdapter()
        registerObserver()
        setOnClickListeners()
    }

    private fun initData() {
        viewModel.getNotifications(userId)
    }

    private fun initAdapter() {
        noticeBoardAdapter = NoticeBoardAdapter().apply {
            itemClickListener = onItemClickListener
        }
        binding.recyclerNotification.apply {
            adapter = noticeBoardAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private val onItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, position: Int) {
            viewModel.deleteNotification(viewModel.notifications.value!![position].id)
        }
    }

    private fun registerObserver() {
        viewModel.notifications.observe(viewLifecycleOwner) {
            binding.nocontentNotification.isVisible = it.isEmpty()
            noticeBoardAdapter.apply {
                notifications = it
                notifyDataSetChanged()
            }
        }
        viewModel.isSuccess.observe(viewLifecycleOwner) {
            if(it) {
                initData()
                viewModel.isSuccess.value = false
            }
        }
    }

    private fun setOnClickListeners() {
        binding.imgNotificationBack.setOnClickListener { requireActivity().onBackPressed() }
        binding.imgNotificationDelete.setOnClickListener {
            viewModel.deleteAllNotification(userId)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}