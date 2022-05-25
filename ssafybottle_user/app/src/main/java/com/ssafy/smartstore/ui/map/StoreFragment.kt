package com.ssafy.smartstore.ui.map

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gun0912.tedpermission.PermissionListener
import com.ssafy.smartstore.R
import com.ssafy.smartstore.databinding.FragmentStoreBinding
import com.ssafy.smartstore.utils.*

class StoreFragment : Fragment() {

    private var _binding: FragmentStoreBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        setOnClickListeners()
    }

    private fun initData() {
        val distance = arguments?.getFloat(DISTANCE)!!
        binding.btnStoreDistance.text = String.format("%.2fkm", distance / 1000)
    }

    private fun setOnClickListeners() {
        binding.textStoreCall.setOnClickListener {
            requestPermission(Manifest.permission.CALL_PHONE, object : PermissionListener {
                override fun onPermissionGranted() {
                    callStore()
                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {}
            })
        }
        binding.textStoreLocation.setOnClickListener {
            findDirection()
        }
        binding.imgStoreBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun callStore() {
        requireActivity().startActivity(
            Intent(
                Intent.ACTION_CALL,
                Uri.parse(
                    "tel:$DEFAULT_TEL"
                )
            )
        )
    }

    private fun findDirection() {
        val gmmIntentUri =
            //Uri.parse("google.navigation:q=$DEFAULT_LATITUDE,$DEFAULT_LONGITUDE&mode=w")
//            Uri.parse("http://maps.google.com/maps?f=d&daddr=경상북도 구미시 황상동 인동중앙로 15&hl=ko");
            Uri.parse("http://maps.google.com/maps?f=d&daddr=경기도 김포시 김포한강2로23번길 72&hl=ko");

        requireActivity().startActivity(
            Intent(
                Intent.ACTION_VIEW,
                gmmIntentUri
            )
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}