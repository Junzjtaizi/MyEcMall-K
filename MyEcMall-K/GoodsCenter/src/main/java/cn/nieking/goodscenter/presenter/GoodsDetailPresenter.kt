package cn.nieking.goodscenter.presenter

import cn.nieking.baselibrary.ext.execute
import cn.nieking.baselibrary.presenter.BasePresenter
import cn.nieking.baselibrary.rx.BaseSubscriber
import cn.nieking.baselibrary.utils.AppPrefsUtils
import cn.nieking.goodscenter.common.GoodsConstant
import cn.nieking.goodscenter.data.protocol.Goods
import cn.nieking.goodscenter.presenter.view.GoodsDetailView
import cn.nieking.goodscenter.service.CartService
import cn.nieking.goodscenter.service.GoodsService
import javax.inject.Inject

class GoodsDetailPresenter @Inject constructor() : BasePresenter<GoodsDetailView>() {

    @Inject
    lateinit var goodsService: GoodsService
    @Inject
    lateinit var cartService: CartService

    fun getGoodsDetail(goodsId: Int) {
        if (!checkNetWork()) {
            println("网络不可用！")
            return
        }
        mView.showLoading()
        goodsService
                .getGoodsDetail(goodsId)
                .execute(object : BaseSubscriber<Goods>(mView) {
                    override fun onNext(t: Goods) {
                        mView.onGetGoodsDetailResult(t)
                    }
                }, lifecycleProvider)
    }

    fun addCart(goodsId: Int, goodsDesc: String, goodsIcon: String, goodsPrice: Long, goodsCount: Int, goodsSku: String) {
        if (!checkNetWork()) {
            println("网络不可用！")
            return
        }
        mView.showLoading()
        cartService
                .addCart(goodsId, goodsDesc, goodsIcon, goodsPrice, goodsCount, goodsSku)
                .execute(object : BaseSubscriber<Int>(mView) {
                    override fun onNext(t: Int) {
                        AppPrefsUtils.putInt(GoodsConstant.SP_CART_SIZE, t)
                        mView.onAddCartResult(t)
                    }
                }, lifecycleProvider)
    }
}