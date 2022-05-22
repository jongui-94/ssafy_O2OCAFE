package com.ssafy.ssafybottle_manager.ui.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.ssafy.ssafybottle_manager.utils.view.getResourceId

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    view.setImageResource(view.getResourceId(url))
}