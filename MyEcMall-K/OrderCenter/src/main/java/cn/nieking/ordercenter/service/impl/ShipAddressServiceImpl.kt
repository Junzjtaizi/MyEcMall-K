package cn.nieking.ordercenter.service.impl

import cn.nieking.baselibrary.ext.convert
import cn.nieking.baselibrary.ext.convertBoolean
import cn.nieking.ordercenter.data.protocol.ShipAddress
import cn.nieking.ordercenter.data.repository.ShipAddressRepository
import cn.nieking.ordercenter.service.ShipAddressService
import rx.Observable
import javax.inject.Inject

class ShipAddressServiceImpl @Inject constructor() : ShipAddressService {

    @Inject
    lateinit var repository: ShipAddressRepository

    override fun addShipAddress(shipUserName: String, shipUserMobile: String, shipAddress: String): Observable<Boolean> {
        return repository.addShipAddress(shipUserName, shipUserMobile, shipAddress).convertBoolean()
    }

    override fun getShipAddressList(): Observable<MutableList<ShipAddress>?> {
        return repository.getShipAddressList().convert()
    }

    override fun editShipAddress(address: ShipAddress): Observable<Boolean> {
        return repository.editShipAddress(address).convertBoolean()
    }

    override fun deleteShipAddress(id: Int): Observable<Boolean> {
        return repository.deleteShipAddress(id).convertBoolean()
    }
}