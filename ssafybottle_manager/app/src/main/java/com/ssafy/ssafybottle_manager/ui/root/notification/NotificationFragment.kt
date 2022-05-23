package com.ssafy.ssafybottle_manager.ui.root.notification

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ssafy.ssafybottle_manager.application.MainViewModel
import com.ssafy.ssafybottle_manager.databinding.FragmentNotificationBinding
import com.ssafy.ssafybottle_manager.ui.adapter.NotificationAdapter
import com.ssafy.ssafybottle_manager.utils.SUCCESS

class NotificationFragment : Fragment() {
    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel : MainViewModel by activityViewModels()
    private lateinit var notificationAdapter : NotificationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        initAdapter()
        observeData()
        setOnClickListeners()
        otherListeners()
    }

    fun initData() {
        mainViewModel.getNotifications()
    }

    private fun initAdapter() {
        notificationAdapter = NotificationAdapter().apply {
            onItemClickListener = notificationRemoveItemClickListener
        }
        binding.recyclerNotification.apply {
            adapter = notificationAdapter
        }
    }

    private val notificationRemoveItemClickListener : (View, Int) -> Unit = { _, position ->
        mainViewModel.deleteNotification(mainViewModel.notifications.value!![position].id)
    }

    private fun observeData() {
        mainViewModel.notifications.observe(viewLifecycleOwner) {
            binding.nocontentNotification.isVisible = it.isEmpty()
            notificationAdapter.apply {
                notifications = it
                notifyDataSetChanged()
            }
        }
        mainViewModel.isNotificationDeleteSuccess.observe(viewLifecycleOwner) {
            when(it) {
                SUCCESS -> {
                    initData()
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.textNotificationRemoveall.setOnClickListener {
            mainViewModel.deleteAllNotification()
        }
    }

    private fun otherListeners() {
        binding.refreshNotification.setOnRefreshListener {
            initData()
            binding.refreshNotification.isRefreshing = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}