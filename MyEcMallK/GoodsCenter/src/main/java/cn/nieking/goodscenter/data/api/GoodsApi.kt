package cn.nieking.goodscenter.data.api

import cn.nieking.baselibrary.data.protocol.BaseResp
import cn.nieking.goodscenter.data.protocol.GetGoodsDetailReq
import cn.nieking.goodscenter.data.protocol.GetGoodsListByKeywordReq
import cn.nieking.goodscenter.data.protocol.GetGoodsListReq
import cn.nieking.goodscenter.data.protocol.Goods
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

/*
    商品接口
 */
interface GoodsApi {
    /*
        获取商品列表
     */
    @POST("goods/getGoodsList")
    fun getGoodsList(@Body req: GetGoodsListReq): Observable<BaseResp<MutableList<Goods>?>>

    /*
        获取商品列表
     */
    @POST("goods/getGoodsListByKeyword")
    fun getGoodsListByKeyword(@Body req: GetGoodsListByKeywordReq): Observable<BaseResp<MutableList<Goods>?>>

    /*
        获取商品详情
     */
    @POST("goods/getGoodsDetail")
    fun getGoodsDetail(@Body req: GetGoodsDetailReq): Observable<BaseResp<Goods>>
}
