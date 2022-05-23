package com.ssafy.ssafybottle_manager.utils

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

fun toMoney(money: Int): String {
    val formatter = DecimalFormat("#,###")
    return formatter.format(money)
}

fun toDateString(time: Date) : String {
    val formatter = SimpleDateFormat("yyyy년 MM월 dd일 H시 m분")
    return formatter.format(time)
}