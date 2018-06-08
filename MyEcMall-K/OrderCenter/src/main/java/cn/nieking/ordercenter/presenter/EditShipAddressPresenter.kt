package cn.nieking.ordercenter.presenter

import cn.nieking.baselibrary.ext.execute
import cn.nieking.baselibrary.presenter.BasePresenter
import cn.nieking.baselibrary.rx.BaseSubscriber
import cn.nieking.ordercenter.data.protocol.ShipAddress
import cn.nieking.ordercenter.presenter.view.EditShipAddressView
import cn.nieking.ordercenter.service.ShipAddressService
import javax.inject.Inject

class EditShipAddressPresenter @Inject constructor() : BasePresenter<EditShipAddressView>() {

    @Inject
    lateinit var orderService: ShipAddressService

    fun addShipAddress(shipUserName: String,
                     shipUserMobile: String,
                     shipAddress: String) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        orderService.addShipAddress(
                shipUserName,
                shipUserMobile,
                shipAddress).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                mView.onAddShipAddressResult(t)
            }
        }, lifecycleProvider)
    }

    fun editShipAddress(address: ShipAddress) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        orderService.editShipAddress(address).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                mView.onEditShipAddressResult(t)
            }
        }, lifecycleProvider)
    }
}