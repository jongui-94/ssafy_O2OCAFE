package com.ssafy.smartstore.utils

import android.content.Context
import androidx.fragment.app.Fragment

fun Fragment.clearUserId() {
    requireContext().getSharedPreferences(USER, Context.MODE_PRIVATE).edit().apply {
        putString(USER_ID, DEFAULT_STRING)
        apply()
    }
}

fun Fragment.saveUserId(id: String) {
    requireContext().getSharedPreferences(USER, Context.MODE_PRIVATE).edit().apply {
        putString(USER_ID, id)
        apply()
    }
}

fun Fragment.setAutoLogin(id: String, pass: String) {
    requireContext().getSharedPreferences(AUTO_LOGIN, Context.MODE_PRIVATE).edit().apply {
        putBoolean(AUTO_LOGIN, true)
        putString(USER_ID, id)
        putString(USER_PASS, pass)
        apply()
    }
}

fun Fragment.unSetAutoLogin() {
    requireContext().getSharedPreferences(AUTO_LOGIN, Context.MODE_PRIVATE).edit().apply {
        putBoolean(AUTO_LOGIN, false)
        apply()
    }
}

fun Fragment.isAutoLogin() : Boolean  =
    requireContext().getSharedPreferences(AUTO_LOGIN, Context.MODE_PRIVATE).getBoolean(AUTO_LOGIN, false)

fun Fragment.getUserInfo() : Map<String, String?> {
    var map = mutableMapOf<String, String?>()

    requireContext().getSharedPreferences(AUTO_LOGIN, Context.MODE_PRIVATE).apply {
        map[USER_ID] = getString(USER_ID, "")
        map[USER_PASS] = getString(USER_PASS, "")
    }

    return map
}

fun Fragment.getUserId(): String = requireContext().getSharedPreferences(
    USER,
    Context.MODE_PRIVATE
).getString(USER_ID, DEFAULT_STRING)!!