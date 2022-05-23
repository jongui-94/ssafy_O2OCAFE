package com.ssafy.ssafybottle_manager.application

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.ssafybottle_manager.R
import com.ssafy.ssafybottle_manager.base.BaseViewModel
import com.ssafy.ssafybottle_manager.data.dto.card.CardDto
import com.ssafy.ssafybottle_manager.data.dto.order.OrderDetailDto
import com.ssafy.ssafybottle_manager.data.dto.order.OrderRequestDto
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

    var liveOrderList = MutableLiveData<MutableList<OrderDetailDto>>()
    var orderList : MutableList<OrderDetailDto> = mutableListOf()
    var totalCost = MutableLiveData(0)

    var userId = MutableLiveData<String>()
    var isBarcodeScanSuccess = MutableLiveData<Int>()

    var isLackOfBalance = MutableLiveData<Int>()
    var isOrderSuccess = MutableLiveData<Int>()

    init {
        menus = mutableListOf(
            PaneMenu(MENU_TITLE, "SSAFYBOTTLE\nMANAGER", null, false),
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

    fun completeOrder(userId: String) {
        viewModelScope.launch(exceptionHandler) {
            // 유저의 잔액 확인
            var cash = 0
            launch(exceptionHandler) {
                repository.checkCash(userId).let {
                    if(it.isSuccessful) {
                        cash = it.body()!!
                    }
                }
            }.join()

            // 잔액 부족하면 결제 취소
            if(cash < totalCost.value!!) {
                isLackOfBalance.postValue(FAILURE)
                return@launch
            }

            // 카드 사용내역 삽입
            repository.postcard(
                CardDto(
                    content = "매장에서 결제",
                    orderId = -1,
                    payment = -totalCost.value!!,
                    userId = userId,
                    payTime = "",
                    id = 0
                )
            )

            // 주문 삽입
            repository.postOrder(OrderRequestDto(
                details = orderList,
                userId = userId,
                orderTable = "1번"
            )).let {
                if(it.isSuccessful) {
                    isOrderSuccess.postValue(SUCCESS)
                } else {
                    isOrderSuccess.postValue(FAILURE)
                }
            }
        }
    }
}