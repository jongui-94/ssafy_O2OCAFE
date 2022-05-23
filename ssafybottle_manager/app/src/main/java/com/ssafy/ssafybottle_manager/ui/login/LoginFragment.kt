package com.ssafy.ssafybottle_manager.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.ssafy.ssafybottle_manager.R
import com.ssafy.ssafybottle_manager.databinding.FragmentLoginBinding
import com.ssafy.ssafybottle_manager.utils.view.hideKeyboard

class LoginFragment : Fragment() {

    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.btnLoginLogin.setOnClickListener {
            if(binding.edtLoginCode.text.toString() != "0000") {
                Toast.makeText(requireContext(), "관리자 코드를 확인해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            findNavController().navigate(R.id.action_loginFragment_to_rootFragment)
        }
        binding.edtLoginCode.setOnEditorActionListener { v, actionId, _ ->
            when(actionId) {
                EditorInfo.IME_ACTION_SEND -> {
                    if(v.text.toString() != "0000") {
                        Toast.makeText(requireContext(), "관리자 코드를 확인해주세요", Toast.LENGTH_SHORT).show()
                    } else {
                        findNavController().navigate(R.id.action_loginFragment_to_rootFragment)
                    }
                    requireActivity().hideKeyboard(binding.edtLoginCode)
                    return@setOnEditorActionListener true
                }
                else -> return@setOnEditorActionListener false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}