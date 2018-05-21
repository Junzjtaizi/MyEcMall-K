package cn.nieking.usercenter.presenter

import cn.nieking.baselibrary.ext.execute
import cn.nieking.baselibrary.presenter.BasePresenter
import cn.nieking.baselibrary.rx.BaseSubscriber
import cn.nieking.usercenter.presenter.view.RegisterView
import cn.nieking.usercenter.presenter.view.UserInfoView
import cn.nieking.usercenter.service.UserService
import com.kotlin.base.utils.NetWorkUtils
import javax.inject.Inject

class UserInfoPresenter @Inject constructor() : BasePresenter<UserInfoView>() {

    @Inject
    lateinit var userService: UserService

}