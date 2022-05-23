package com.ssafy.ssafybottle_manager.application

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import com.ssafy.ssafybottle_manager.R
import com.ssafy.ssafybottle_manager.ui.login.LoginFragment
import com.ssafy.ssafybottle_manager.ui.root.RootFragment
import com.ssafy.ssafybottle_manager.utils.FAILURE
import com.ssafy.ssafybottle_manager.utils.SUCCESS

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private var backPressedTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startBarcodeReader() {
        val integrator = IntentIntegrator(this)
        integrator.setOrientationLocked(false)
        integrator.setPrompt("바코드로 결제를 시작합니다.")
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents != null) {
                viewModel.userId.value = result.contents
                viewModel.isBarcodeScanSuccess.value = SUCCESS
            } else {
                viewModel.isBarcodeScanSuccess.value = FAILURE
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onBackPressed() {
        val curFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host)!!.childFragmentManager.fragments[0]
        if (curFragment is RootFragment || curFragment is LoginFragment) {
            val currentTime = System.currentTimeMillis()
            val elapsedTime = currentTime - backPressedTime
            backPressedTime = currentTime

            if (elapsedTime in 0..2000) {
                finish()
            } else {
                Toast.makeText(this, "뒤로가기 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
            }
        } else {
            super.onBackPressed()
        }
    }
}