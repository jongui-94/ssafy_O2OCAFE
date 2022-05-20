package com.ssafy.smartstore.ui.root

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.RemoteException
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.gun0912.tedpermission.PermissionListener
import com.ssafy.smartstore.R
import com.ssafy.smartstore.databinding.FragmentRootBinding
import com.ssafy.smartstore.ui.dialog.BeaconNotificationDialog
import com.ssafy.smartstore.ui.root.card.CardFragment
import com.ssafy.smartstore.ui.root.home.HomeFragment
import com.ssafy.smartstore.ui.root.mypage.MyPageFragment
import com.ssafy.smartstore.ui.root.order.MenuDetailFragment
import com.ssafy.smartstore.utils.*
import org.altbeacon.beacon.*

class RootFragment : Fragment(), BeaconConsumer {

    private var _binding: FragmentRootBinding? = null
    private val binding get() = _binding!!

    private var homeFragment: HomeFragment? = null
    private var menuDetailFragment: MenuDetailFragment? = null
    private var myPageFragment: MyPageFragment? = null
    private var cardFragment: CardFragment? = null

    private lateinit var beaconManager: BeaconManager
    private lateinit var bluetoothManager: BluetoothManager
    private var bluetoothAdapter: BluetoothAdapter? = null
    private var needBLERequest = true
    private val region = Region("altbeacon", null, null, null)

    companion object {
        var curFragment: String = FRAGMENT_HOME
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRootBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setStatusBarBackground()
        initChildFragment()
        requestLocationPermission()
        initBeaconManager()
        initBluetoothManager()
        setOnClickListeners()
        startScan()
    }

    private fun initChildFragment() {
        homeFragment = childFragmentManager.findFragmentByTag(FRAGMENT_HOME) as HomeFragment?
        menuDetailFragment =
            childFragmentManager.findFragmentByTag(FRAGMENT_MENU_DETAIL) as MenuDetailFragment?
        myPageFragment = childFragmentManager.findFragmentByTag(FRAGMENT_MY_PAGE) as MyPageFragment?
        cardFragment = childFragmentManager.findFragmentByTag(FRAGMENT_CARD) as CardFragment?

        val transaction = childFragmentManager.beginTransaction()

        if (homeFragment == null) {
            homeFragment = HomeFragment()
            transaction.add(R.id.fragmentcontainer_root, homeFragment!!, FRAGMENT_HOME)
        }
        if (menuDetailFragment == null) {
            menuDetailFragment = MenuDetailFragment()
            transaction.add(R.id.fragmentcontainer_root, menuDetailFragment!!, FRAGMENT_MENU_DETAIL)
        }
        if (cardFragment == null) {
            cardFragment = CardFragment()
            transaction.add(R.id.fragmentcontainer_root, cardFragment!!, FRAGMENT_CARD)
        }
        if (myPageFragment == null) {
            myPageFragment = MyPageFragment()
            transaction.add(R.id.fragmentcontainer_root, myPageFragment!!, FRAGMENT_MY_PAGE)
        }

        transaction.commit()

        showHideFragment(curFragment)
    }

    private fun requestLocationPermission() {
        requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, object : PermissionListener {
            override fun onPermissionGranted() {}
            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                Toast.makeText(requireContext(), "권한획득에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initBeaconManager() {
        beaconManager = BeaconManager.getInstanceForApplication(requireContext())
        beaconManager.beaconParsers.add(BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"))
    }

    private fun initBluetoothManager() {
        bluetoothManager =
            requireContext().getSystemService(AppCompatActivity.BLUETOOTH_SERVICE) as BluetoothManager
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
            Toast.makeText(requireContext(), "블루투스가 켜지지 않았습니다.", Toast.LENGTH_SHORT).show()
        }

        // 위치 정보 권한 허용 및 GPS Enable 여부 확인
        requestLocationPermission()

        Log.d("StoreFragment_싸피", "startScan: beacon Scan start")
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
                Log.d("StoreFragment_싸피", "비콘을 발견하였습니다.------------${region.toString()}")
                beaconManager.startRangingBeaconsInRegion(region!!)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }

        override fun didExitRegion(region: Region?) {
            try {
                Log.d("StoreFragment_싸피", "비콘을 찾을 수 없습니다.")
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
                    "StoreFragment_싸피",
                    "distance: " + beacon.distance + " Major : " + beacon.id2 + ", Minor" + beacon.id3
                )

                // 다이얼로그 띄우기
                BeaconNotificationDialog().show(childFragmentManager, "BeaconNotificationDialog")

                // 비콘 스캔 중지
                stopBeaconScan()
            }
        }
    }

    // 찾고자 하는 Beacon 이 맞는지, 정해둔 거리 내부인지 확인
    private fun isYourBeacon(beacon: Beacon): Boolean {
        return (beacon.distance <= STORE_DISTANCE)
    }

    private fun setOnClickListeners() {
        binding.bottomnavigationRoot.setOnNavigationItemSelectedListener(itemSelectedListener)
        binding.imgRootNotification.setOnClickListener {
            findNavController()
                .navigate(R.id.action_rootFragment_to_notificationFragment)
        }
    }

    private val itemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            var tag = FRAGMENT_HOME
            when (menuItem.itemId) {
                R.id.HomeFragment -> {
                    tag = FRAGMENT_HOME
                }
                R.id.MenuDetailFragment -> {
                    tag = FRAGMENT_MENU_DETAIL
                }
                R.id.CardFragment -> {
                    tag = FRAGMENT_CARD
                }
                R.id.MyPageFragment -> {
                    tag = FRAGMENT_MY_PAGE
                }
            }
            showHideFragment(tag)
            true
        }

    private fun showHideFragment(tag: String) {
        val transaction = childFragmentManager.beginTransaction()
        when (tag) {
            FRAGMENT_HOME -> {
                transaction.apply {
                    show(homeFragment!!)
                    hide(menuDetailFragment!!)
                    hide(cardFragment!!)
                    hide(myPageFragment!!)
                }
            }
            FRAGMENT_MENU_DETAIL -> {
                transaction.apply {
                    hide(homeFragment!!)
                    show(menuDetailFragment!!)
                    hide(cardFragment!!)
                    hide(myPageFragment!!)
                }
            }
            FRAGMENT_CARD -> {
                transaction.apply {
                    hide(homeFragment!!)
                    hide(menuDetailFragment!!)
                    show(cardFragment!!)
                    hide(myPageFragment!!)
                }
            }
            FRAGMENT_MY_PAGE -> {
                transaction.apply {
                    hide(homeFragment!!)
                    hide(menuDetailFragment!!)
                    hide(cardFragment!!)
                    show(myPageFragment!!)
                }
            }
        }
        transaction.commit()
    }

    private fun saveCurrentFragment() {
        childFragmentManager.fragments.forEach {
            if (it.isVisible) {
                curFragment = it.tag!!
            }
        }
    }

    override fun onPause() {
        super.onPause()
        saveCurrentFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopBeaconScan()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        curFragment = FRAGMENT_HOME
        setStatusBarOriginTransparent()
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

    override fun getApplicationContext(): Context = applicationContext

    override fun unbindService(connection: ServiceConnection?) {
        requireActivity().unbindService(connection!!)
    }

    override fun bindService(intent: Intent?, connection: ServiceConnection?, mode: Int): Boolean {
        return requireActivity().bindService(intent, connection!!, mode)
    }
}