package cn.nieking.goodscenter.data.protocol

/*
    按分类搜索商品
 */
data class GetGoodsListReq(val categoryId: Int,val pageNo: Int)
