package com.ssafy.smartstore.ui.dlg

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.gun0912.tedpermission.PermissionListener
import com.ssafy.smartstore.databinding.DialogStoreInfoBinding
import com.ssafy.smartstore.utils.DEFAULT_LONGITUDE
import com.ssafy.smartstore.utils.DEFAULT_TEL
import com.ssafy.smartstore.utils.requestPermission

class StoreInfoDialog : DialogFragment() {

    private var _binding: DialogStoreInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogStoreInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.textDlgStoreinfoCall.setOnClickListener {
            requestPermission(Manifest.permission.CALL_PHONE, object : PermissionListener {
                override fun onPermissionGranted() {
                    callStore()
                }
                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) { }
            })
        }
        binding.textDlgStoreinfoRoad.setOnClickListener {
            findDirection()
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
        val gmmIntentUri = Uri.parse("google.navigation:q=$DEFAULT_LONGITUDE,$DEFAULT_LONGITUDE&mode=w")
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