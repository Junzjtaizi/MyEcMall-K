package cn.nieking.ordercenter.service

import cn.nieking.ordercenter.data.protocol.Order
import rx.Observable

interface OrderService {

    fun getOrderById(orderId: Int): Observable<Order>
    fun submitOrder(order: Order): Observable<Boolean>
    fun getOrderList(orderStatus: Int): Observable<MutableList<Order>?>
    fun cancelOrder(orderId: Int): Observable<Boolean>
    fun confirmOrder(orderId: Int): Observable<Boolean>
}