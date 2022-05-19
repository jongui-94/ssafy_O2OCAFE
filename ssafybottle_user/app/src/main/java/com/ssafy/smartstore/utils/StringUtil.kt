package com.ssafy.smartstore.utils

import java.text.DecimalFormat

fun toMoney(money: Int): String {
    val formatter = DecimalFormat("#,###")
    return formatter.format(money)
}