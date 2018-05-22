package cn.nieking.usercenter.presenter.view

import cn.nieking.baselibrary.presenter.view.BaseView

interface UserInfoView : BaseView {

    fun onGetUploadTokenResult(result: String)
}