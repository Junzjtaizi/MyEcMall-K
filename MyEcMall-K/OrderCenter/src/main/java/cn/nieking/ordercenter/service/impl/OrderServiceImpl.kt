package cn.nieking.ordercenter.service.impl

import cn.nieking.baselibrary.ext.convert
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
}