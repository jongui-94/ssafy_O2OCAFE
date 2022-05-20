package com.ssafy.ssafybottle_manager.utils

import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission

fun requestPermission(permission: String, permissionListener: PermissionListener) {
    TedPermission.create()
        .setPermissionListener(permissionListener)
        .setDeniedMessage("권한을 허용해주세요.")
        .setPermissions(permission)
        .check()
}

fun Fragment.checkLocationServicesStatus(): Boolean {
    val locationManager =
        requireActivity().getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager
    return (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
}

// 권한 체크
fun Fragment.checkPermissions(permissions: List<String>): Boolean {
    permissions.forEach {
        if(ContextCompat.checkSelfPermission(requireContext(), it) != PackageManager.PERMISSION_GRANTED)
            return false
    }
    return true
}