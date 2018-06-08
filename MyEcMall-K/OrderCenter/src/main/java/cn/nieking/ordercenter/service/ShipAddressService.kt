package cn.nieking.ordercenter.service

import cn.nieking.ordercenter.data.protocol.ShipAddress
import rx.Observable

interface ShipAddressService {

    fun addShipAddress(shipUserName: String,
                       shipUserMobile: String,
                       shipAddress: String): Observable<Boolean>

    fun getShipAddressList(): Observable<MutableList<ShipAddress>?>

    fun editShipAddress(address: ShipAddress): Observable<Boolean>

    fun deleteShipAddress(id: Int): Observable<Boolean>
}