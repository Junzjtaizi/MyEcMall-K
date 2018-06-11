package cn.nieking.paysdk.presenter.view

import cn.nieking.baselibrary.presenter.view.BaseView

interface PayView : BaseView {

    fun onGetPaySignResult(result: String)
    fun onPayOrderResult(result: Boolean)
}