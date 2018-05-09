package cn.nieking.baselibrary.ui.activity

import cn.nieking.baselibrary.presenter.BasePresenter
import cn.nieking.baselibrary.presenter.view.BaseView
import javax.inject.Inject

open class BaseMvpActivity<T: BasePresenter<*>> : BaseActivity(), BaseView {
    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun onError() {

    }

    @Inject
    lateinit var mPresenter: T
}