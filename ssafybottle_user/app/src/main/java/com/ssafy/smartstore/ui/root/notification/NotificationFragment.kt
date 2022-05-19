package com.ssafy.smartstore.ui.root.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.smartstore.databinding.FragmentNotificationBinding
import com.ssafy.smartstore.ui.adapter.NoticeBoardAdapter
import com.ssafy.smartstore.ui.adapter.OnItemClickListener

class NotificationFragment : Fragment() {

    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NotificationViewModel by viewModels()
    private lateinit var noticeBoardAdapter: NoticeBoardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        registerObserver()
        setOnClickListeners()
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
            viewModel.deleteNotification(viewModel.notifications.value?.get(position)!!)
        }
    }

    private fun registerObserver() {
        viewModel.notifications.observe(viewLifecycleOwner) {
            noticeBoardAdapter.apply {
                notifications = it
                notifyDataSetChanged()
            }
        }
    }

    private fun setOnClickListeners() {
        binding.imgNotificationBack.setOnClickListener { requireActivity().onBackPressed() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}