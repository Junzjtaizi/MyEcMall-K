package cn.nieking.ordercenter.presenter.view

import cn.nieking.baselibrary.presenter.view.BaseView

interface EditShipAddressView : BaseView {

    fun onAddShipAddressResult(result: Boolean)
    fun onEditShipAddressResult(result: Boolean)
}
