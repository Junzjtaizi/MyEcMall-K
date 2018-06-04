package cn.nieking.goodscenter.service.impl

import cn.nieking.baselibrary.ext.convert
import cn.nieking.goodscenter.data.protocol.Goods
import cn.nieking.goodscenter.data.repository.GoodsRepository
import cn.nieking.goodscenter.service.GoodsService
import rx.Observable
import javax.inject.Inject

class GoodsServiceImpl @Inject constructor() : GoodsService {

    @Inject
    lateinit var repository: GoodsRepository

    override fun getGoodsList(categoryId: Int, pageNo: Int): Observable<MutableList<Goods>?> {
        return repository.getGoodsList(categoryId, pageNo).convert()
    }

    override fun getGoodsListByKeyword(keyword: String, pageNo: Int): Observable<MutableList<Goods>?> {
        return repository.getGoodsListByKeyword(keyword, pageNo).convert()
    }
}