package com.ssafy.ssafybottle_manager.application

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.ssafybottle_manager.R
import com.ssafy.ssafybottle_manager.base.BaseViewModel
import com.ssafy.ssafybottle_manager.data.dto.pane.PaneMenu
import com.ssafy.ssafybottle_manager.data.dto.product.ProductDto
import com.ssafy.ssafybottle_manager.data.repository.Repository
import com.ssafy.ssafybottle_manager.utils.*
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel() {
    private val repository = Repository.get()

    var menus = mutableListOf<PaneMenu>()
        private set

    private val _products = MutableLiveData<List<ProductDto>>()
    val products: LiveData<List<ProductDto>> get() = _products
    var isProductSuccess = MutableLiveData<Int>()

    private val _dessert = MutableLiveData<List<ProductDto>>()
    val dessert: LiveData<List<ProductDto>> get() = _dessert
    var isDessertSuccess = MutableLiveData<Int>()

    private val _beverage = MutableLiveData<List<ProductDto>>()
    val beverage: LiveData<List<ProductDto>> get() = _beverage
    var isBeverageSuccess = MutableLiveData<Int>()


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

    fun getProducts() {
        viewModelScope.launch(exceptionHandler) {
            repository.getProducts().let {
                if (it.isSuccessful) {
                    _products.postValue(it.body())
                    isProductSuccess.postValue(SUCCESS)
                } else {
                    isProductSuccess.postValue(FAILURE)
                }
            }
        }
    }

    fun getDessert() {
        viewModelScope.launch(exceptionHandler) {
            repository.getDessert().let {
                if (it.isSuccessful) {
                    _dessert.postValue(it.body())
                    isDessertSuccess.postValue(SUCCESS)
                } else {
                    isDessertSuccess.postValue(FAILURE)
                }
            }
        }
    }

    fun getBeverage() {
        viewModelScope.launch(exceptionHandler) {
            repository.getBeverage().let {
                if (it.isSuccessful) {
                    _beverage.postValue(it.body())
                    isBeverageSuccess.postValue(SUCCESS)
                } else {
                    isBeverageSuccess.postValue(FAILURE)
                }
            }
        }
    }
}