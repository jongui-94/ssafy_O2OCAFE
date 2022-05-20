package com.ssafy.smartstore.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.core.Repo
import com.ssafy.smartstore.R
import com.ssafy.smartstore.data.dto.user.UserDto
import com.ssafy.smartstore.data.repository.Repository
import com.ssafy.smartstore.databinding.FragmentLoginBinding
import com.ssafy.smartstore.utils.USER_ID
import com.ssafy.smartstore.utils.getToken
import com.ssafy.smartstore.utils.saveUserId
import com.ssafy.smartstore.utils.setAutoLogin
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
        setOnClickListeners()
    }

    private fun registerObserver() {
        isSuccess.observe(viewLifecycleOwner) {
            binding.progressbarLoginLoading.isVisible = false
            if (it) {
                setAutoLogin(binding.edtLoginId.text.toString(), binding.edtLoginPw.text.toString())
                saveUserId(binding.edtLoginId.text.toString())
                navigateToRootFragment()
            } else {
                binding.progressbarLoginLoading.isVisible = false
                Toast.makeText(requireContext(), "로그인 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setOnClickListeners() {
        binding.btnLoginLogin.setOnClickListener {
            val id = binding.edtLoginId.text.toString()
            val pw = binding.edtLoginPw.text.toString()

            if (id.isEmpty() || pw.isEmpty()) {
                Toast.makeText(requireContext(), "모두 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            loginUser(id, pw)
        }
        binding.imgLoginBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun loginUser(id: String, pass: String) {
        binding.progressbarLoginLoading.isVisible = true
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
                        postToken(id)
                        return@launch
                    }
                }
                isSuccess.postValue(false)
            }
        }
    }

    private suspend fun postToken(userId: String) {
        val token = getToken()
        Repository.get().postToken(mapOf("userId" to userId, "token" to token))
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        isSuccess.postValue(false)
    }

    private fun navigateToRootFragment() {
        findNavController().apply {
            navigate(R.id.action_loginFragment_to_rootFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}