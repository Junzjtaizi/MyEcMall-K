package cn.nieking.ordercenter.presenter

import cn.nieking.baselibrary.ext.execute
import cn.nieking.baselibrary.presenter.BasePresenter
import cn.nieking.baselibrary.rx.BaseSubscriber
import cn.nieking.ordercenter.data.protocol.ShipAddress
import cn.nieking.ordercenter.presenter.view.ShipAddressView
import cn.nieking.ordercenter.service.ShipAddressService
import javax.inject.Inject

class ShipAddressPresenter @Inject constructor() : BasePresenter<ShipAddressView>() {

    @Inject
    lateinit var orderService: ShipAddressService

    fun getShipAddressList() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        orderService.getShipAddressList().execute(object : BaseSubscriber<MutableList<ShipAddress>?>(mView) {
            override fun onNext(t: MutableList<ShipAddress>?) {
                mView.onGetShipAddressListResult(t)
            }
        }, lifecycleProvider)
    }

    fun setDefaultShipAddress(address: ShipAddress) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        orderService.editShipAddress(address).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                mView.onSetDefaultResult(t)
            }
        }, lifecycleProvider)
    }

    fun deleteShipAddress(id: Int) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        orderService.deleteShipAddress(id).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                mView.onDeleteShipAddressResult(t)
            }
        }, lifecycleProvider)
    }
}