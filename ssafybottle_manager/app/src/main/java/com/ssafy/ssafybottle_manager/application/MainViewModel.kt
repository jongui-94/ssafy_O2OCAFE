package com.ssafy.ssafybottle_manager.application

import com.ssafy.ssafybottle_manager.R
import com.ssafy.ssafybottle_manager.base.BaseViewModel
import com.ssafy.ssafybottle_manager.data.dto.pane.PaneMenu
import com.ssafy.ssafybottle_manager.utils.*

class MainViewModel : BaseViewModel() {
    var menus = mutableListOf<PaneMenu>()
        private set

    init {
        menus = mutableListOf(
            PaneMenu(MENU_TITLE, "싸피보틀 관리", null, false),
            PaneMenu(MENU_ORDER, "주문", R.drawable.ic_cart, true),
            PaneMenu(MENU_ORDER_MANAGEMENT, "주문 관리", R.drawable.ic_order, false),
            PaneMenu(MENU_SALES_MANAGEMENT, "매출 관리", R.drawable.ic_dollar, false),
            PaneMenu(MENU_NOTIFICATION, "알림", R.drawable.ic_notification, false),
            PaneMenu(MENU_SETTING, "세팅", R.drawable.ic_setting, false),
        )
    }

    fun changeMenu(idx: Int) {
        menus.forEachIndexed { index, paneMenu ->
            paneMenu.isSelected = index == idx
        }
    }
}