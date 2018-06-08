package cn.nieking.ordercenter.service

import cn.nieking.ordercenter.data.protocol.Order
import rx.Observable

interface OrderService {

    fun getOrderById(orderId: Int): Observable<Order>
}