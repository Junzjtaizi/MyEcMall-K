package cn.nieking.usercenter.presenter

import cn.nieking.baselibrary.ext.execute
import cn.nieking.baselibrary.presenter.BasePresenter
import cn.nieking.baselibrary.rx.BaseSubscriber
import cn.nieking.usercenter.data.protocol.UserInfo
import cn.nieking.usercenter.presenter.view.UserInfoView
import cn.nieking.usercenter.service.UploadService
import cn.nieking.usercenter.service.UserService
import javax.inject.Inject

class UserInfoPresenter @Inject constructor() : BasePresenter<UserInfoView>() {

    @Inject
    lateinit var userService: UserService
    @Inject
    lateinit var uploadService: UploadService

    fun getUploadToken() {
        if (!checkNetWork()) return
        mView.showLoading()
        uploadService.getUploadToken().execute(object : BaseSubscriber<String>(mView) {
            override fun onNext(t: String) {
                mView.onGetUploadTokenResult(t)
            }
        }, lifecycleProvider)
    }

    fun editUser(userIcon: String, userName: String, userGender: String, userSign: String) {
        if (!checkNetWork()) return
        mView.showLoading()
        userService.editUser(userIcon, userName, userGender, userSign).execute(object : BaseSubscriber<UserInfo>(mView) {
            override fun onNext(t: UserInfo) {
                mView.onEditUserResult(t)
            }
        }, lifecycleProvider)
    }
}