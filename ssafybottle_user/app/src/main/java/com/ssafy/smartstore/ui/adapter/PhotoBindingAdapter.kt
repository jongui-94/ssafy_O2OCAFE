package com.ssafy.smartstore.ui.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.ssafy.smartstore.utils.view.getResourceId

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    Glide.with(view)
        .load(view.getResourceId(url))
        .into(view)
    //view.setImageResource(view.getResourceId(url))
}
