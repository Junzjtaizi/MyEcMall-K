package cn.nieking.ordercenter.presenter.view

import cn.nieking.baselibrary.presenter.view.BaseView
import cn.nieking.ordercenter.data.protocol.ShipAddress

interface ShipAddressView : BaseView {

    fun onGetShipAddressListResult(result: MutableList<ShipAddress>?)
    fun onSetDefaultResult(result: Boolean)
    fun onDeleteShipAddressResult(result: Boolean)
}
