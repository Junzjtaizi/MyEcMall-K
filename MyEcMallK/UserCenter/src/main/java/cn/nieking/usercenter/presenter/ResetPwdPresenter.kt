package cn.nieking.usercenter.presenter

import cn.nieking.baselibrary.ext.execute
import cn.nieking.baselibrary.presenter.BasePresenter
import cn.nieking.baselibrary.rx.BaseSubscriber
import cn.nieking.usercenter.presenter.view.ForgetPwdView
import cn.nieking.usercenter.presenter.view.RegisterView
import cn.nieking.usercenter.presenter.view.ResetPwdView
import cn.nieking.usercenter.service.UserService
import com.kotlin.base.utils.NetWorkUtils
import javax.inject.Inject

class ResetPwdPresenter @Inject constructor() : BasePresenter<ResetPwdView>() {

    @Inject
    lateinit var userService: UserService

    fun resetPwd(mobile: String, pwd: String) {
        if (!checkNetWork()) {
            println("网络不可用！")
            return
        }
        mView.showLoading()
        userService.resetPwd(mobile, pwd)
                .execute(object : BaseSubscriber<Boolean>(mView) {
                    override fun onNext(t: Boolean) {
                        if (t) mView.onResetPwdResult("密码重置成功")
                    }
                }, lifecycleProvider)
    }
}