package com.ssafy.ssafybottle_manager.application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.google.zxing.integration.android.IntentIntegrator
import com.ssafy.ssafybottle_manager.R
import com.ssafy.ssafybottle_manager.utils.FAILURE
import com.ssafy.ssafybottle_manager.utils.SUCCESS

class MainActivity : AppCompatActivity() {

    private val viewModel : MainViewModel by viewModels()

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
}