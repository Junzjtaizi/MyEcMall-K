package cn.nieking.goodscenter.presenter.view

import cn.nieking.baselibrary.presenter.view.BaseView
import cn.nieking.goodscenter.data.protocol.Category

interface CategoryView : BaseView {

    fun onGetCategoryResult(result: MutableList<Category>?)
}