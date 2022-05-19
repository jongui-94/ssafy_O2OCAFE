package com.ssafy.smartstore.application

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.ssafy.smartstore.R
import com.ssafy.smartstore.application.SmartStoreApplication.Companion.tableName
import com.ssafy.smartstore.databinding.ActivityMainBinding
import com.ssafy.smartstore.ui.login.LoginFragment
import com.ssafy.smartstore.ui.root.RootFragment

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding : ActivityMainBinding

    private var nfcAdapter: NfcAdapter? = null
    private lateinit var pIntent: PendingIntent
    private lateinit var filters: Array<IntentFilter>

    private var backPressedTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNfcAdapter()
        initIntent()
    }

    private fun initNfcAdapter() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (nfcAdapter == null) {
            //inish()
            Toast.makeText(this, "기기가 nfc 지원을 하지 않습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initIntent() {
        val intent = Intent(this, MainActivity::class.java)
        // SingleTop 설정
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP


        // PendingIntent 설정
        pIntent = PendingIntent.getActivity(this, 0, intent, 0)

        // 필터 설정
        val tagFilter = IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)
        filters = arrayOf(tagFilter)
    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent!!.action == NfcAdapter.ACTION_NDEF_DISCOVERED ||
            intent.action == NfcAdapter.ACTION_TAG_DISCOVERED ||
            intent.action == NfcAdapter.ACTION_TECH_DISCOVERED
        ) {

            Log.d("MainActivity_싸피", "onNewIntent()")
            getNfcMessage(intent)
        }
    }

    private fun getNfcMessage(intent: Intent?) {
        val msgs = getNdefMessages(intent!!)
        if(msgs != null) {
            val storeId = getPayload(msgs!![0]!!.records[0])
            Log.d("MainActivity_싸피", storeId) //t,1

            val messages = storeId.split(',')
            when(messages[0]) {
                "t" ->{
                    Toast.makeText(this, "${messages[1]}번 테이블 번호가 등록되었습니다.", Toast.LENGTH_SHORT).show()
                    tableName = messages[1]
                }
            }
        }
    }

    private fun getNdefMessages(intent: Intent): Array<NdefMessage?>? {
        var msgs: Array<NdefMessage?>? = null

        val rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
        if (rawMsgs != null) {
            msgs = arrayOfNulls(rawMsgs.size)
            for (i in rawMsgs.indices) {
                msgs[i] = rawMsgs[i] as NdefMessage?
            }
        }
        return msgs
    }

    private fun getPayload(recInfo: NdefRecord): String =
        String(recInfo.payload, 3, recInfo.payload.size - 3)

    override fun onResume() {
        super.onResume()
        nfcAdapter?.enableForegroundDispatch(this, pIntent, filters, null)
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter?.disableForegroundDispatch(this)
    }

    override fun onBackPressed() {
        val curFragment = supportFragmentManager.findFragmentById(R.id.nav_host)!!.childFragmentManager.fragments[0]
        if(curFragment is RootFragment || curFragment is LoginFragment) {
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