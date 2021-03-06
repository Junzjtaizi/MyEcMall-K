package cn.nieking.ordercenter.presenter.view

import cn.nieking.baselibrary.presenter.view.BaseView
import cn.nieking.ordercenter.data.protocol.Order

interface OrderDetailView : BaseView {

    fun onGetOrderByIdResult(result: Order)
}
