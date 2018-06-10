package cn.nieking.ordercenter.service.impl

import cn.nieking.baselibrary.ext.convert
import cn.nieking.baselibrary.ext.convertBoolean
import cn.nieking.ordercenter.data.protocol.Order
import cn.nieking.ordercenter.data.repository.OrderRepository
import cn.nieking.ordercenter.service.OrderService
import rx.Observable
import javax.inject.Inject

class OrderServiceImpl @Inject constructor() : OrderService {

    @Inject
    lateinit var repository: OrderRepository

    override fun getOrderById(orderId: Int): Observable<Order> {
        return repository.getOrderById(orderId).convert()
    }

    override fun submitOrder(order: Order): Observable<Boolean> {
        return repository.submitOrder(order).convertBoolean()
    }

    override fun getOrderList(orderStatus: Int): Observable<MutableList<Order>?> {
        return repository.getOrderList(orderStatus).convert()
    }

    override fun confirmOrder(orderId: Int): Observable<Boolean> {
        return repository.confirmOrder(orderId).convertBoolean()
    }

    override fun cancelOrder(orderId: Int): Observable<Boolean> {
        return repository.cancelOrder(orderId).convertBoolean()
    }
}