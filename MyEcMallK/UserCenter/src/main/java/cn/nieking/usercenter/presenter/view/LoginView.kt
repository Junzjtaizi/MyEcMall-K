package cn.nieking.usercenter.presenter.view

import cn.nieking.baselibrary.presenter.view.BaseView
import cn.nieking.usercenter.data.protocol.UserInfo

interface LoginView : BaseView {

    fun onLoginResult(result: UserInfo)
}