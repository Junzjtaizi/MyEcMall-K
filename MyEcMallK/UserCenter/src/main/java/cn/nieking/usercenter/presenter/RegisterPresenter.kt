package cn.nieking.usercenter.presenter

import cn.nieking.baselibrary.presenter.BasePresenter
import cn.nieking.usercenter.presenter.view.RegisterView

class RegisterPresenter : BasePresenter<RegisterView>() {

    fun register(mobile: String, verifyCode: String) {
        mView.onRegisterResult(true)
    }
}