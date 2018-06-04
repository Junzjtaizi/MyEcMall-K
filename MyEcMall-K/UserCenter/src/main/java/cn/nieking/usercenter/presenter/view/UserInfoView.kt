package cn.nieking.usercenter.presenter.view

import cn.nieking.baselibrary.presenter.view.BaseView
import cn.nieking.usercenter.data.protocol.UserInfo

interface UserInfoView : BaseView {

    fun onGetUploadTokenResult(result: String)
    fun onEditUserResult(result: UserInfo)
}