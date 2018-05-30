package cn.nieking.goodscenter.service

import cn.nieking.goodscenter.data.protocol.Goods
import rx.Observable

interface GoodsService {
    fun getGoodsList(categoryId: Int, pageNo: Int): Observable<MutableList<Goods>?>
}