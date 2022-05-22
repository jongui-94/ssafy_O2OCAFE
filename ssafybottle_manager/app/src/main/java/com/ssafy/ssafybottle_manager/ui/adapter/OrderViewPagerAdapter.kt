package com.ssafy.ssafybottle_manager.ui.adapter

import com.ssafy.ssafybottle_manager.ui.root.order.DessertFragment
import com.ssafy.ssafybottle_manager.ui.root.order.ProductFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ssafy.ssafybottle_manager.ui.root.order.BeverageFragment
import com.ssafy.ssafybottle_manager.utils.NUM_TABS

class OrderViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when(position) {
            0 -> return ProductFragment()
            1 -> return BeverageFragment()
            2 -> return DessertFragment()
        }
        return DessertFragment()
    }
}