package cn.nieking.goodscenter.presenter

import cn.nieking.baselibrary.ext.execute
import cn.nieking.baselibrary.presenter.BasePresenter
import cn.nieking.baselibrary.rx.BaseSubscriber
import cn.nieking.goodscenter.data.protocol.Category
import cn.nieking.goodscenter.presenter.view.CategoryView
import cn.nieking.goodscenter.service.CategoryService
import javax.inject.Inject

class CategoryPresenter @Inject constructor() : BasePresenter<CategoryView>() {

    @Inject
    lateinit var categoryService: CategoryService

    fun getCategory(parentId: Int) {
        if (!checkNetWork()) {
            println("网络不可用！")
            return
        }
        mView.showLoading()
        categoryService.getCategory(parentId)
                .execute(object : BaseSubscriber<MutableList<Category>?>(mView) {
                    override fun onNext(t: MutableList<Category>?) {
                        mView.onGetCategoryResult(t)
                    }
                }, lifecycleProvider)
    }
}