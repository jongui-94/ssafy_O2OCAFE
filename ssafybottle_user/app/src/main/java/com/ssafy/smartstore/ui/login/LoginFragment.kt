package com.ssafy.smartstore.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.ssafy.smartstore.R
import com.ssafy.smartstore.data.dto.user.UserDto
import com.ssafy.smartstore.data.repository.Repository
import com.ssafy.smartstore.databinding.FragmentLoginBinding
import com.ssafy.smartstore.utils.*
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.net.SocketException
import java.net.UnknownHostException

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val isSuccess = MutableLiveData<Boolean>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerObserver()
        checkAutoLogin()
        setOnClickListeners()
    }

    private fun checkAutoLogin() {
        // 자동 로그인이면
        if (isAutoLogin()) {
            val userInfo = getUserInfo()

            binding.apply {
                checkboxLoginAutologin.isChecked = true
                edtLoginId.setText(userInfo[USER_ID]!!)
                edtLoginPw.setText(userInfo[USER_PASS]!!)
            }

            loginUser(userInfo[USER_ID]!!, userInfo[USER_PASS]!!)
        }
    }

    private fun registerObserver() {
        isSuccess.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(requireContext(), "로그인 성공", Toast.LENGTH_SHORT).show()
                saveAutoLoginState()
                saveUserId(binding.edtLoginId.text.toString())
                navigateToRootFragment()
            } else {
                Toast.makeText(requireContext(), "로그인 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveAutoLoginState() {
        if (binding.checkboxLoginAutologin.isChecked) {
            setAutoLogin(
                binding.edtLoginId.text.toString(),
                binding.edtLoginPw.text.toString()
            )
        }
    }

    private fun navigateToRootFragment() {
        findNavController().apply {
            navigate(R.id.action_loginFragment_to_rootFragment)
        }
    }

    private fun setOnClickListeners() {
        binding.textLoginJoin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_joinFragment)
        }
        binding.btnLoginLogin.setOnClickListener {
            val id = binding.edtLoginId.text.toString()
            val pass = binding.edtLoginPw.text.toString()

            if (id.isEmpty() || pass.isEmpty()) {
                Toast.makeText(requireContext(), "모두 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            loginUser(id, pass)
        }
    }

    private fun loginUser(id: String, pass: String) {
        val user = UserDto(
            id = id,
            name = "",
            pass = pass,
            stampList = emptyList(),
            stamps = 0
        )
        CoroutineScope(Dispatchers.IO).launch(exceptionHandler) {
            Repository.get().login(user).let {
                if (it.isSuccessful) {
                    it.body()?.let {
                        isSuccess.postValue(true)
                        return@launch
                    }
                }
                isSuccess.postValue(false)
            }
        }
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        isSuccess.postValue(false)
    }

    private fun setStatusBarTransParent() {
        binding.constraintLoginContainer.setStatusBarTransparent(requireActivity())
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