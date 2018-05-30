package cn.nieking.goodscenter.data.protocol

import cn.nieking.goodscenter.data.protocol.CartGoods

/*
    提交购物车
 */
data class SubmitCartReq(val goodsList: List<CartGoods>, val totalPrice: Long)
