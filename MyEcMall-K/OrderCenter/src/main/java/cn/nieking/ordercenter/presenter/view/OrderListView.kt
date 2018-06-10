package cn.nieking.ordercenter.presenter.view

import cn.nieking.baselibrary.presenter.view.BaseView
import cn.nieking.ordercenter.data.protocol.Order

interface OrderListView : BaseView {

    fun onGetOrderListResult(result: MutableList<Order>?)
    fun onConfirmOrderResult(result: Boolean)
    fun onCancelOrderResult(result: Boolean)
}
