package com.ssafy.smartstore.ui.login

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.withCreated
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.identity.GetSignInIntentRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.database.core.Repo
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
    private val isGoogleSignInSuccess = MutableLiveData<Boolean>()
    private var user: Map<String, String?>? = null

    private var googleLoginUser : UserDto? = null

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initGoogleSignIn()
        registerObserver()
        checkAutoLogin()
        setOnClickListeners()
    }

    private fun initGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
    }

    private fun registerObserver() {
        isSuccess.observe(viewLifecycleOwner) {
            binding.progressbarLoginmainLoading.isVisible = false
            if (it) {
                user?.let { u ->
                    saveUserId(u[USER_ID]!!)
                    navigateToRootFragment()
                    return@observe
                }
                googleLoginUser?.let { g ->
                    setAutoLogin(g.id, g.pass)
                    saveUserId(g.id)
                    navigateToRootFragment()
                    return@observe
                }
            } else {
                unSetAutoLogin()
                signOut()
                googleLoginUser = null
                user = null
                Toast.makeText(requireContext(), "로그인 실패", Toast.LENGTH_SHORT).show()
            }
        }
        isGoogleSignInSuccess.observe(viewLifecycleOwner) {
            binding.progressbarLoginmainLoading.isVisible = false
            if(it) {
                loginUser(googleLoginUser!!.id, googleLoginUser!!.pass)
            } else {
                Toast.makeText(requireContext(), "구글 로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signOut() {
        mGoogleSignInClient.signOut().addOnCompleteListener(requireActivity()) { }
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
        binding.btnLoginmainGoogle.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        googleSignInLauncher.launch(mGoogleSignInClient.signInIntent)
    }

    private val googleSignInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                var task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleSignInResult(task)
            }
        }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            account?.let {
                val email: String = account.email.toString()
                val name = account.displayName.toString()
                val id = email.split("@")[0]

                googleLoginLogic(
                    UserDto(
                        id = id,
                        name = name,
                        pass = email,
                        stampList = emptyList(),
                        stamps = 0
                    )
                )
                return
            }
            Toast.makeText(requireContext(), "구글 로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.d("LoginMain_싸피", "error" + e.printStackTrace().toString())
        }
    }

    private fun googleLoginLogic(user: UserDto) {
        googleLoginUser = user
        Log.d("LoginMain_싸피", user.toString())

        CoroutineScope(Dispatchers.IO).launch(exceptionHandler) {
            var result = false
            launch(exceptionHandler) {
                Repository.get().checkUserId(user.id).let { response ->
                    if(response.isSuccessful) {
                        // 중복 아이디 있을때
                        if(response.body()!!) {
                            result = true
                        }
                    }
                }
            }.join()

            // 중복 아이디 없을때
            if(!result) {
                Log.d("LoginMain_싸피", "중복 아이디 없음")
                // 회원가입
                Repository.get().insertUser(user).let { response ->
                    if(response.isSuccessful) {
                        isGoogleSignInSuccess.postValue(true)
                    } else {
                        isGoogleSignInSuccess.postValue(false)
                    }
                }
            } else {
                // 로그인 시도
                Log.d("LoginMain_싸피", "중복 아이디 있으므로 로그인 시도")
                launch(Dispatchers.Main) {
                    loginUser(user.id, user.pass)
                }
                Log.d("LoginMain_싸피", "구글 로그인 성공 후 로그인 시도")
                Log.d("LoginMain_싸피", user.toString())
            }
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
        Log.d("LoginMain_싸피", "loginUser: " + user.toString())
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
        Log.d("LoginMainFragment_싸피", "postToken: ${token}")
        Repository.get().postToken(mapOf("userId" to userId, "token" to token))
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d("LoginMain_싸피", "오류 발생")
        Log.d("LoginMain_싸피", throwable.toString())
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