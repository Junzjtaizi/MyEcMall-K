package cn.nieking.usercenter.presenter

import cn.nieking.baselibrary.ext.execute
import cn.nieking.baselibrary.presenter.BasePresenter
import cn.nieking.baselibrary.rx.BaseSubscriber
import cn.nieking.usercenter.presenter.view.RegisterView
import cn.nieking.usercenter.service.UserService
import javax.inject.Inject

class RegisterPresenter @Inject constructor() : BasePresenter<RegisterView>() {

    @Inject
    lateinit var userService: UserService

    fun register(mobile: String, pwd: String, verifyCode: String) {
        if (!checkNetWork()) {
            println("网络不可用！")
            return
        }
        mView.showLoading()
        userService.register(mobile, pwd, verifyCode)
                .execute(object : BaseSubscriber<Boolean>(mView) {
                    override fun onNext(t: Boolean) {
                        if (t) mView.onRegisterResult("注册成功")
                    }
                }, lifecycleProvider)
    }
}