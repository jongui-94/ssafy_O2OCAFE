package com.ssafy.smartstore.application

import android.Manifest
import android.app.PendingIntent
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.os.Bundle
import android.os.RemoteException
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.gun0912.tedpermission.PermissionListener
import com.ssafy.smartstore.R
import com.ssafy.smartstore.application.SmartStoreApplication.Companion.isCoupon
import com.ssafy.smartstore.application.SmartStoreApplication.Companion.tableName
import com.ssafy.smartstore.databinding.ActivityMainBinding
import com.ssafy.smartstore.ui.dialog.BeaconNotificationDialog
import com.ssafy.smartstore.ui.login.LoginMainFragment
import com.ssafy.smartstore.ui.root.RootFragment
import com.ssafy.smartstore.utils.STORE_DISTANCE
import com.ssafy.smartstore.utils.requestPermission
import org.altbeacon.beacon.*

class MainActivity : AppCompatActivity(), BeaconConsumer{

    private lateinit var binding : ActivityMainBinding

    private val mainViewModel : MainViewModel by viewModels()
    private var nfcAdapter: NfcAdapter? = null
    private lateinit var pIntent: PendingIntent
    private lateinit var filters: Array<IntentFilter>

    private var backPressedTime = 0L

    // Beacon
    private lateinit var beaconManager: BeaconManager
    private lateinit var bluetoothManager: BluetoothManager
    private var bluetoothAdapter: BluetoothAdapter? = null
    private var needBLERequest = true
    private val region = Region("altbeacon", null, null, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNfcAdapter()
        initIntent()
        requestLocationPermission()
        initBeaconManager()
        initBluetoothManager()
        startScan()
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
                "c" -> {
                    if(isCoupon) {
                        mainViewModel.coupon.value = messages[1].toInt()
                    }
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
        if(curFragment is RootFragment || curFragment is LoginMainFragment) {
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

    // Beacon
    override fun onBeaconServiceConnect() {
        beaconManager.addMonitorNotifier(monitorNotifier)
        beaconManager.addRangeNotifier(rangeNotifier)
        try {
            beaconManager.startMonitoringBeaconsInRegion(region)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    private fun requestLocationPermission() {
        requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, object : PermissionListener {
            override fun onPermissionGranted() {}
            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                Toast.makeText(this@MainActivity, "권한획득에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initBeaconManager() {
        beaconManager = BeaconManager.getInstanceForApplication(this)
        beaconManager.beaconParsers.add(BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"))
    }

    private fun initBluetoothManager() {
        bluetoothManager = getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter
    }

    private fun isEnableBLEService(): Boolean = bluetoothAdapter!!.isEnabled

    private fun requestEnableBLE() {
        requestBLEActivity.launch(
            Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        )
    }

    private val requestBLEActivity: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        // 사용자의 블루투스 사용이 가능한지 확인
        if (isEnableBLEService()) {
            needBLERequest = false
            startScan()
        }
    }

    private fun startScan() {
        // 블루투스 Enable 확인
        if (!isEnableBLEService()) {
            requestEnableBLE()
            Toast.makeText(this, "블루투스가 켜지지 않았습니다.", Toast.LENGTH_SHORT).show()
        }

        // 위치 정보 권한 허용 및 GPS Enable 여부 확인
        requestLocationPermission()

        Log.d("RootFragment_싸피", "startScan: beacon Scan start")
        // Beacon Service bind
        beaconManager.bind(this)
    }

    private fun stopBeaconScan() {
        beaconManager.stopMonitoringBeaconsInRegion(region)
        beaconManager.stopRangingBeaconsInRegion(region)
        beaconManager.unbind(this)
    }

    private val monitorNotifier = object : MonitorNotifier {
        override fun didEnterRegion(region: Region?) {
            try {
                Log.d("RootFragment_싸피", "비콘을 발견하였습니다.------------${region.toString()}")
                beaconManager.startRangingBeaconsInRegion(region!!)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }

        override fun didExitRegion(region: Region?) {
            try {
                Log.d("RootFragment_싸피", "비콘을 찾을 수 없습니다.")
                beaconManager.stopRangingBeaconsInRegion(region!!)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }

        override fun didDetermineStateForRegion(state: Int, region: Region?) {}
    }

    private val rangeNotifier = RangeNotifier { beacons, _ ->
        for (beacon in beacons) {
            // Major, Minor 로 Beacon 구별, 1미터 이내에 들어오면 메세지 출력
            if (isYourBeacon(beacon)) {
                // 한번만 띄우기 위한 조건
                Log.d(
                    "RootFragment_싸피",
                    "distance: " + beacon.distance + " Major : " + beacon.id2 + ", Minor" + beacon.id3
                )

                // 다이얼로그 띄우기
                //BeaconNotificationDialog().show(childFragmentManager, "BeaconNotificationDialog")
                BeaconNotificationDialog().show(supportFragmentManager, "BeaconNotificationDialog")

                // 비콘 스캔 중지
                stopBeaconScan()
            }
        }
    }

    // 찾고자 하는 Beacon 이 맞는지, 정해둔 거리 내부인지 확인
    private fun isYourBeacon(beacon: Beacon): Boolean {
        return (beacon.distance <= STORE_DISTANCE)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopBeaconScan()
    }
}