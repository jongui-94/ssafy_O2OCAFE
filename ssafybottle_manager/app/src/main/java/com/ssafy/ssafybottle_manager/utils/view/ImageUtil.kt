package com.ssafy.ssafybottle_manager.utils.view

import android.view.View

fun View.getResourceId(url: String?): Int =
    resources.getIdentifier("@drawable/${url!!.split(".")[0]}", "drawable", "com.ssafy.ssafybottle_manager")
