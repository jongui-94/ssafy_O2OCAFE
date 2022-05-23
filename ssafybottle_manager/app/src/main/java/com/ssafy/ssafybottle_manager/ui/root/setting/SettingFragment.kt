package com.ssafy.ssafybottle_manager.ui.root.setting

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ssafy.ssafybottle_manager.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClickListeners()
    }

    private fun onClickListeners() {
        binding.textSettingShutdown.setOnClickListener {
            showShutdownDialog { _ , _ ->
                requireActivity().finish()
            }
        }
    }

    private fun showShutdownDialog(listener: DialogInterface.OnClickListener) {
        AlertDialog
            .Builder(requireContext())
            .setTitle("프로그램 종료")
            .setMessage("정말로 관리자 프로그램을 종료 하시겠습니까?")
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