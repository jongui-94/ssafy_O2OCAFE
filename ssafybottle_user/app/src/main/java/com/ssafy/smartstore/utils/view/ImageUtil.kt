package com.ssafy.smartstore.utils

import android.view.View

fun View.getResourceId(url: String?): Int =
    resources.getIdentifier("@drawable/${url!!.split(".")[0]}", "drawable", "com.ssafy.smartstore")
