package cn.nieking.goodscenter.data.protocol

/*
    删除购物车商品请求
 */
data class DeleteCartReq(val cartIdList: List<Int> = arrayListOf())
