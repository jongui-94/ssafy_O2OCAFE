package com.ssafy.smartstore.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ssafy.smartstore.data.dto.user.UserDto
import com.ssafy.smartstore.databinding.FragmentJoinBinding
import com.ssafy.smartstore.utils.retrofit.FetchState
import com.ssafy.smartstore.utils.setStatusBarOrigin
import com.ssafy.smartstore.utils.setStatusBarTransparent

class JoinFragment : Fragment() {

    private var _binding: FragmentJoinBinding? = null
    private val binding get() = _binding!!

    private val viewModel: JoinViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentJoinBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerObserver()
        setOnClickListeners()
    }

    private fun registerObserver() {
        viewModel.isComplete.observe(viewLifecycleOwner) {
            if(it) {
                Toast.makeText(requireContext(), "회원가입 성공", Toast.LENGTH_SHORT).show()
                requireActivity().onBackPressed()
            } else {
                Toast.makeText(requireContext(), "회원가입 실패", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.isOverlap.observe(viewLifecycleOwner) {
            if(it) {
                Toast.makeText(requireContext(), "중복된 아이디 입니다.", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.fetchState.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "상품정보를 받아오는데 실패했습니다.", Toast.LENGTH_SHORT).show()
            when(it) {
                FetchState.BAD_INTERNET -> { }
                FetchState.PARSE_ERROR -> {}
                FetchState.WRONG_CONNECTION -> {}
                FetchState.FAIL -> {}
            }
        }
    }

    private fun setOnClickListeners() {
        binding.btnJoinJoin.setOnClickListener {
            val id = binding.edtJoinId.text.toString()
            val pass = binding.edtJoinPw.text.toString()
            val name = binding.edtJoinName.text.toString()

            if (id.isEmpty() || pass.isEmpty() || name.isEmpty()) {
                Toast.makeText(requireContext(), "모두 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(!binding.checkboxJoinTermsofservice.isChecked) {
                Toast.makeText(requireContext(), "이용약관에 동의해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.insertUser(
                UserDto(
                    id = id,
                    name = name,
                    pass = pass,
                    stampList = emptyList(),
                    stamps = 0
                )
            )
        }
        binding.imgJoinBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun setStatusBarTransParent() {
        binding.constraintJoinInnerContainer.setStatusBarTransparent(requireActivity())
    }

    private fun setStatusBarOrigin() {
        requireActivity().setStatusBarOrigin()
    }

    override fun onResume() {
        super.onResume()
        setStatusBarTransParent()
    }

    override fun onPause() {
        super.onPause()
        setStatusBarOrigin()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}