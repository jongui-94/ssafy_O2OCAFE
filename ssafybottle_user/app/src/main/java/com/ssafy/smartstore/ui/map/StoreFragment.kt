package com.ssafy.smartstore.ui.map

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gun0912.tedpermission.PermissionListener
import com.ssafy.smartstore.R
import com.ssafy.smartstore.databinding.FragmentStoreBinding
import com.ssafy.smartstore.utils.DEFAULT_LATITUDE
import com.ssafy.smartstore.utils.DEFAULT_LONGITUDE
import com.ssafy.smartstore.utils.DEFAULT_TEL
import com.ssafy.smartstore.utils.requestPermission

class StoreFragment : Fragment() {

    private var _binding : FragmentStoreBinding? = null
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

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.imgStoreCall.setOnClickListener {
            requestPermission(Manifest.permission.CALL_PHONE, object : PermissionListener {
                override fun onPermissionGranted() {
                    callStore()
                }
                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) { }
            })
        }
        binding.imgStoreLocation.setOnClickListener {
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
        val gmmIntentUri = Uri.parse("google.navigation:q=$DEFAULT_LATITUDE,$DEFAULT_LONGITUDE&mode=w")
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