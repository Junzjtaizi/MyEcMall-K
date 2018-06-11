package cn.nieking.paysdk.service

import rx.Observable

interface PayService {

    fun getPaySign(orderId: Int, totalPrice: Long): Observable<String>

    fun payOrder(orderId: Int): Observable<Boolean>
}