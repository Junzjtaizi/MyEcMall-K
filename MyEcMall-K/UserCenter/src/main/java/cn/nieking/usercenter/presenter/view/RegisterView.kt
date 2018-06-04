package cn.nieking.usercenter.presenter.view

import cn.nieking.baselibrary.presenter.view.BaseView

interface RegisterView : BaseView {

    fun onRegisterResult(result: String)
}