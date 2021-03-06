package cn.nieking.goodscenter.data.repository

import cn.nieking.baselibrary.data.net.RetrofitFactory
import cn.nieking.baselibrary.data.protocol.BaseResp
import cn.nieking.goodscenter.data.api.GoodsApi
import cn.nieking.goodscenter.data.protocol.GetGoodsDetailReq
import cn.nieking.goodscenter.data.protocol.GetGoodsListByKeywordReq
import cn.nieking.goodscenter.data.protocol.GetGoodsListReq
import cn.nieking.goodscenter.data.protocol.Goods
import rx.Observable
import javax.inject.Inject

/*
    商品数据层
 */
class GoodsRepository @Inject constructor() {

    /*
        根据分类搜索商品
     */
    fun getGoodsList(categoryId: Int, pageNo: Int): Observable<BaseResp<MutableList<Goods>?>> {
        return RetrofitFactory.instance
                .create(GoodsApi::class.java)
                .getGoodsList(GetGoodsListReq(categoryId, pageNo))
    }

    /*
        根据关键字搜索商品
     */
    fun getGoodsListByKeyword(keyword: String, pageNo: Int): Observable<BaseResp<MutableList<Goods>?>> {
        return RetrofitFactory.instance
                .create(GoodsApi::class.java)
                .getGoodsListByKeyword(GetGoodsListByKeywordReq(keyword, pageNo))
    }

    /*
        商品详情
     */
    fun getGoodsDetail(goodsId: Int): Observable<BaseResp<Goods>> {
        return RetrofitFactory.instance
                .create(GoodsApi::class.java)
                .getGoodsDetail(GetGoodsDetailReq(goodsId))
    }
}
