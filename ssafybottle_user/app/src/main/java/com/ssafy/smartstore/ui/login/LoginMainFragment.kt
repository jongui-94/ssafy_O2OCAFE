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
import com.ssafy.smartstore.R
import com.ssafy.smartstore.data.dto.user.UserDto
import com.ssafy.smartstore.data.repository.Repository
import com.ssafy.smartstore.databinding.FragmentLoginMainBinding
import com.ssafy.smartstore.utils.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginMainFragment : Fragment() {

    private var _binding: FragmentLoginMainBinding? = null
    private val binding get() = _binding!!

    private val isSuccess = MutableLiveData<Boolean>()
    private var user : Map<String, String?>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerObserver()
        checkAutoLogin()
        setOnClickListeners()
    }

    private fun registerObserver() {
        binding.progressbarLoginmainLoading.isVisible = false
        isSuccess.observe(viewLifecycleOwner) {
            if (it) {
                user?.let {
                    saveUserId(user!![USER_ID]!!)
                }
                navigateToRootFragment()
            } else {
                unSetAutoLogin()
                Toast.makeText(requireContext(), "로그인 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkAutoLogin() {
        // 자동 로그인이면
        if (isAutoLogin()) {
            user = getUserInfo()
            loginUser(user!![USER_ID]!!, user!![USER_PASS]!!)
        }
    }

    private fun setOnClickListeners() {
        binding.btnLoginmainLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginMainFragment_to_loginFragment)
        }
        binding.textLoginmainJoin.setOnClickListener {
            findNavController().navigate(R.id.action_loginMainFragment_to_joinFragment)
        }
    }

    private fun loginUser(id: String, pass: String) {
        binding.progressbarLoginmainLoading.isVisible = true
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

    private fun navigateToRootFragment() {
        findNavController().apply {
            navigate(R.id.action_loginMainFragment_to_rootFragment)
        }
    }

    private fun setStatusBarTransParent() {
        binding.constraintLoginmainInnerContainer.setStatusBarTransparent(requireActivity())
    }

    private fun setStatusBarOrigin() {
        requireActivity().setStatusBarOrigin()
    }

    override fun onStart() {
        super.onStart()
        setStatusBarTransParent()
    }

    override fun onStop() {
        super.onStop()
        setStatusBarOrigin()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}