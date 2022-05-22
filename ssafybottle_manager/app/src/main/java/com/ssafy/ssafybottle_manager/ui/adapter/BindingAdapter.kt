package com.ssafy.ssafybottle_manager.ui.adapter

import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.ssafy.ssafybottle_manager.R
import com.ssafy.ssafybottle_manager.utils.view.getResourceId

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    Glide.with(view)
        .load(view.getResourceId(url))
        .into(view)
}

@BindingAdapter("imgResId")
fun bindImageFromResource(view: ImageView, resId: Int) {
    Glide.with(view)
        .load(resId)
        .placeholder(resId)
        .into(view)
}

@BindingAdapter("backColor")
fun bindBackgroundBySelected(layout: ConstraintLayout, isSelected: Boolean) {
    layout.setBackgroundResource(if(isSelected) R.drawable.bg_pane_item_select else R.drawable.ripple_pane_item)
}

