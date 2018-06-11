package cn.nieking.paysdk.service.impl

import cn.nieking.baselibrary.ext.convert
import cn.nieking.baselibrary.ext.convertBoolean
import cn.nieking.paysdk.data.repository.PayRepository
import cn.nieking.paysdk.service.PayService
import rx.Observable
import javax.inject.Inject

class PayServiceImpl @Inject constructor() : PayService {

    @Inject
    lateinit var repository: PayRepository

    override fun getPaySign(orderId: Int, totalPrice: Long): Observable<String> {
        return repository.getPaySign(orderId, totalPrice).convert()
    }

    override fun payOrder(orderId: Int): Observable<Boolean> {
        return repository.payOrder(orderId).convertBoolean()
    }
}