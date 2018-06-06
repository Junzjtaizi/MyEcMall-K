package cn.nieking.goodscenter.presenter.view

import cn.nieking.baselibrary.presenter.view.BaseView
import cn.nieking.goodscenter.data.protocol.Goods

interface GoodsDetailView : BaseView {

    fun onGetGoodsDetailResult(result: Goods)
    fun onAddCartResult(result: Int)
}