package cn.nieking.ordercenter.presenter.view

import cn.nieking.baselibrary.presenter.view.BaseView
import cn.nieking.ordercenter.data.protocol.Order

interface OrderConfirmView : BaseView {

    fun onGetOrderByIdResult(result: Order)
}
