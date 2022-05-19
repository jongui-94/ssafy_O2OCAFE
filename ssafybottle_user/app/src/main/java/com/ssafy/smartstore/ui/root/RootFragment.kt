package com.ssafy.smartstore.ui.root

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ssafy.smartstore.R
import com.ssafy.smartstore.databinding.FragmentRootBinding
import com.ssafy.smartstore.ui.root.card.CardFragment
import com.ssafy.smartstore.ui.root.home.HomeFragment
import com.ssafy.smartstore.ui.root.mypage.MyPageFragment
import com.ssafy.smartstore.ui.root.order.MenuDetailFragment
import com.ssafy.smartstore.utils.*

class RootFragment : Fragment() {

    private var _binding: FragmentRootBinding? = null
    private val binding get() = _binding!!

    private var homeFragment: HomeFragment? = null
    private var menuDetailFragment: MenuDetailFragment? = null
    private var myPageFragment: MyPageFragment? = null
    private var cardFragment: CardFragment? = null

    companion object {
        var curFragment: String = FRAGMENT_HOME
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

        setStatusBarBackground()
        initChildFragment()
        setOnClickListeners()
    }

    private fun initChildFragment() {
        homeFragment = childFragmentManager.findFragmentByTag(FRAGMENT_HOME) as HomeFragment?
        menuDetailFragment =
            childFragmentManager.findFragmentByTag(FRAGMENT_MENU_DETAIL) as MenuDetailFragment?
        myPageFragment = childFragmentManager.findFragmentByTag(FRAGMENT_MY_PAGE) as MyPageFragment?
        cardFragment = childFragmentManager.findFragmentByTag(FRAGMENT_CARD) as CardFragment?

        val transaction = childFragmentManager.beginTransaction()

        if (homeFragment == null) {
            homeFragment = HomeFragment()
            transaction.add(R.id.fragmentcontainer_root, homeFragment!!, FRAGMENT_HOME)
        }
        if (menuDetailFragment == null) {
            menuDetailFragment = MenuDetailFragment()
            transaction.add(R.id.fragmentcontainer_root, menuDetailFragment!!, FRAGMENT_MENU_DETAIL)
        }
        if (cardFragment == null) {
            cardFragment = CardFragment()
            transaction.add(R.id.fragmentcontainer_root, cardFragment!!, FRAGMENT_CARD)
        }
        if (myPageFragment == null) {
            myPageFragment = MyPageFragment()
            transaction.add(R.id.fragmentcontainer_root, myPageFragment!!, FRAGMENT_MY_PAGE)
        }

        transaction.commit()

        showHideFragment(curFragment)
    }

    private fun setOnClickListeners() {
        binding.bottomnavigationRoot.setOnNavigationItemSelectedListener(itemSelectedListener)
    }

    private val itemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            var tag = FRAGMENT_HOME
            when (menuItem.itemId) {
                R.id.HomeFragment -> {
                    tag = FRAGMENT_HOME
                }
                R.id.MenuDetailFragment -> {
                    tag = FRAGMENT_MENU_DETAIL
                }
                R.id.CardFragment -> {
                    tag = FRAGMENT_CARD
                }
                R.id.MyPageFragment -> {
                    tag = FRAGMENT_MY_PAGE
                }
            }
            showHideFragment(tag)
            true
        }

    private fun showHideFragment(tag: String) {
        val transaction = childFragmentManager.beginTransaction()
        when (tag) {
            FRAGMENT_HOME -> {
                transaction.apply {
                    show(homeFragment!!)
                    hide(menuDetailFragment!!)
                    hide(cardFragment!!)
                    hide(myPageFragment!!)
                }
            }
            FRAGMENT_MENU_DETAIL -> {
                transaction.apply {
                    hide(homeFragment!!)
                    show(menuDetailFragment!!)
                    hide(cardFragment!!)
                    hide(myPageFragment!!)
                }
            }
            FRAGMENT_CARD -> {
                transaction.apply {
                    hide(homeFragment!!)
                    hide(menuDetailFragment!!)
                    show(cardFragment!!)
                    hide(myPageFragment!!)
                }
            }
            FRAGMENT_MY_PAGE -> {
                transaction.apply {
                    hide(homeFragment!!)
                    hide(menuDetailFragment!!)
                    hide(cardFragment!!)
                    show(myPageFragment!!)
                }
            }
        }
        transaction.commit()
    }

    private fun saveCurrentFragment() {
        childFragmentManager.fragments.forEach {
            if (it.isVisible) {
                curFragment = it.tag!!
            }
        }
    }

    override fun onPause() {
        super.onPause()
        saveCurrentFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        curFragment = FRAGMENT_HOME
        setStatusBarOriginTransparent()
    }
}