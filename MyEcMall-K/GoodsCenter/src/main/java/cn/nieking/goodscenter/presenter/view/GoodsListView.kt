package cn.nieking.goodscenter.presenter.view

import cn.nieking.baselibrary.presenter.view.BaseView
import cn.nieking.goodscenter.data.protocol.Goods

interface GoodsListView : BaseView {

    fun onGetGoodsListResult(result: MutableList<Goods>?)
}