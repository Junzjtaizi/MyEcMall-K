package cn.nieking.usercenter.data.api

import cn.nieking.baselibrary.data.protocol.BaseResp
import cn.nieking.usercenter.data.protocol.*
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

interface UploadApi {
    @POST("common/getUploadToken")
    fun getUploadToken(): Observable<BaseResp<String>>
}