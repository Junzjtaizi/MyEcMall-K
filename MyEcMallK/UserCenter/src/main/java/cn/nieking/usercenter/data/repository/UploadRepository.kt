package cn.nieking.usercenter.data.repository

import cn.nieking.baselibrary.data.net.RetrofitFactory
import cn.nieking.baselibrary.data.protocol.BaseResp
import cn.nieking.usercenter.data.api.UploadApi
import cn.nieking.usercenter.data.api.UserApi
import cn.nieking.usercenter.data.protocol.RegisterReq
import rx.Observable
import javax.inject.Inject

class UploadRepository @Inject constructor() {

    fun getUploadToken(): Observable<BaseResp<String>> {
        return RetrofitFactory.instance
                .create(UploadApi::class.java)
                .getUploadToken()
    }
}