package com.ssafy.ssafybottle_manager.ui.root

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.ssafybottle_manager.R
import com.ssafy.ssafybottle_manager.application.MainViewModel
import com.ssafy.ssafybottle_manager.databinding.FragmentRootBinding
import com.ssafy.ssafybottle_manager.ui.adapter.PaneAdapter
import com.ssafy.ssafybottle_manager.ui.root.notification.NotificationFragment
import com.ssafy.ssafybottle_manager.ui.root.order.OrderFragment
import com.ssafy.ssafybottle_manager.ui.root.order_management.OrderManagementFragment
import com.ssafy.ssafybottle_manager.ui.root.sales.SalesFragment
import com.ssafy.ssafybottle_manager.ui.root.setting.SettingFragment
import com.ssafy.ssafybottle_manager.utils.*

class RootFragment : Fragment() {

    private var _binding: FragmentRootBinding? = null
    private val binding get() = _binding!!

    private var orderFragment: OrderFragment? = null
    private var orderManagementFragment: OrderManagementFragment? = null
    private var salesFragment: SalesFragment? = null
    private var notificationFragment: NotificationFragment? = null
    private var settingFragment: SettingFragment? = null

    private val mainViewModel : MainViewModel by viewModels()
    private lateinit var paneAdapter: PaneAdapter

    companion object {
        var curFragment: String = FRAGMENT_ORDER
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRootBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initChildFragment()
        initAdapter()
    }

    private fun initChildFragment() {
        childFragmentManager.let {
            orderFragment = it.findFragmentByTag(FRAGMENT_ORDER) as OrderFragment?
            orderManagementFragment = it.findFragmentByTag(FRAGMENT_ORDER_MANAGEMENT) as OrderManagementFragment?
            salesFragment = it.findFragmentByTag(FRAGMENT_SALES) as SalesFragment?
            notificationFragment = it.findFragmentByTag(FRAGMENT_NOTIFICATION) as NotificationFragment?
            settingFragment = it.findFragmentByTag(FRAGMENT_SETTING) as SettingFragment?
        }

        childFragmentManager.beginTransaction().apply {
            if(orderFragment == null) {
                orderFragment = OrderFragment()
                add(R.id.fragmentcontainer_root, orderFragment!!, FRAGMENT_ORDER)
            }
            if(orderManagementFragment == null) {
                orderManagementFragment = OrderManagementFragment()
                add(R.id.fragmentcontainer_root, orderManagementFragment!!, FRAGMENT_ORDER_MANAGEMENT)
                hide(orderManagementFragment!!)
            }
            if(salesFragment == null) {
                salesFragment = SalesFragment()
                add(R.id.fragmentcontainer_root, salesFragment!!, FRAGMENT_SALES)
                hide(salesFragment!!)
            }
            if(notificationFragment == null) {
                notificationFragment = NotificationFragment()
                add(R.id.fragmentcontainer_root, notificationFragment!!, FRAGMENT_NOTIFICATION)
                hide(notificationFragment!!)
            }
            if(settingFragment == null) {
                settingFragment = SettingFragment()
                add(R.id.fragmentcontainer_root, settingFragment!!, FRAGMENT_SETTING)
                hide(settingFragment!!)
            }
            commit()
        }
        showHideFragment(curFragment)
    }

    private fun showHideFragment(curFragment: String) {
        childFragmentManager.beginTransaction().apply {
            childFragmentManager.fragments.forEach {
                if(it.tag == curFragment) {
                    show(it)
                } else {
                    hide(it)
                }
            }
            commit()
        }
    }

    private fun initAdapter() {
        paneAdapter = PaneAdapter(mainViewModel.menus).apply {
            onItemClickListener = menuItemClickListener
        }
        binding.recyclerRootPane.apply {
            adapter = paneAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private val menuItemClickListener: (View, Int) -> Unit = { _, position ->
        when(mainViewModel.menus[position].id) {
            MENU_ORDER -> { curFragment = FRAGMENT_ORDER }
            MENU_ORDER_MANAGEMENT -> { curFragment = FRAGMENT_ORDER_MANAGEMENT }
            MENU_SALES_MANAGEMENT -> { curFragment = FRAGMENT_SALES }
            MENU_NOTIFICATION -> { curFragment = FRAGMENT_NOTIFICATION
                notificationFragment!!.initData()
            }
            MENU_SETTING -> { curFragment = FRAGMENT_SETTING }
        }
        showHideFragment(curFragment)
        mainViewModel.changeMenu(position)
        paneAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}