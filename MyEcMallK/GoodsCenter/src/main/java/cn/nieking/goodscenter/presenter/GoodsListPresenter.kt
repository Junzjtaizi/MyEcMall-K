package cn.nieking.goodscenter.presenter

import cn.nieking.baselibrary.ext.execute
import cn.nieking.baselibrary.presenter.BasePresenter
import cn.nieking.baselibrary.rx.BaseSubscriber
import cn.nieking.goodscenter.data.protocol.Goods
import cn.nieking.goodscenter.presenter.view.GoodsListView
import cn.nieking.goodscenter.service.GoodsService
import javax.inject.Inject

class GoodsListPresenter @Inject constructor() : BasePresenter<GoodsListView>() {

    @Inject
    lateinit var goodsService: GoodsService

    fun getGoodsList(categoryId: Int, pageNo: Int) {
        if (!checkNetWork()) {
            println("网络不可用！")
            return
        }
        mView.showLoading()
        goodsService
                .getGoodsList(categoryId, pageNo)
                .execute(object : BaseSubscriber<MutableList<Goods>?>(mView) {
                    override fun onNext(t: MutableList<Goods>?) {
                        mView.onGetGoodsListResult(t)
                    }
                }, lifecycleProvider)
    }
}