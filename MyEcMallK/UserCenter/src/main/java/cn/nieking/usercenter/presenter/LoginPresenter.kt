package cn.nieking.usercenter.presenter

import cn.nieking.baselibrary.ext.execute
import cn.nieking.baselibrary.presenter.BasePresenter
import cn.nieking.baselibrary.rx.BaseSubscriber
import cn.nieking.usercenter.data.protocol.UserInfo
import cn.nieking.usercenter.presenter.view.LoginView
import cn.nieking.usercenter.service.UserService
import javax.inject.Inject

class LoginPresenter @Inject constructor() : BasePresenter<LoginView>() {

    @Inject
    lateinit var userService: UserService

    fun login(mobile: String, pwd: String, pushId: String) {
        if (!checkNetWork()) {
            println("网络不可用！")
            return
        }
        mView.showLoading()
        userService.login(mobile, pwd, pushId)
                .execute(object : BaseSubscriber<UserInfo>(mView) {
                    override fun onNext(t: UserInfo) {
                        mView.onLoginResult(t)
                    }
                }, lifecycleProvider)
    }
}