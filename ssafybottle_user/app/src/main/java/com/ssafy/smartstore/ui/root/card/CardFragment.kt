package com.ssafy.smartstore.ui.root.card

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.smartstore.databinding.FragmentCardBinding
import com.ssafy.smartstore.ui.adapter.CardHistoryAdapter
import com.ssafy.smartstore.utils.createBarcode
import com.ssafy.smartstore.utils.getUserId
import com.ssafy.smartstore.utils.view.getPxFromDp

class CardFragment : Fragment() {

    private var _binding : FragmentCardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        setOnClickListeners()
    }

    private fun initView() {
        val userId = getUserId()
        val barcode = Bitmap.createScaledBitmap(createBarcode(userId), requireContext().getPxFromDp(340f), requireContext().getPxFromDp(56f), true)
        binding.imgCardBarcode.setImageBitmap(barcode)
    }

    private fun setOnClickListeners() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}