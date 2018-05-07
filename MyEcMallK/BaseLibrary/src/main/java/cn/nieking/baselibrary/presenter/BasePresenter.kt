package cn.nieking.baselibrary.presenter

import cn.nieking.baselibrary.presenter.view.BaseView

open class BasePresenter<T: BaseView> {
    lateinit var mView: T
}