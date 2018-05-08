package cn.nieking.usercenter.data.api

import cn.nieking.baselibrary.data.protocol.BaseResp
import cn.nieking.usercenter.data.protocol.RegisterReq
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

interface UserApi {
    @POST("userCenter/register")
    fun register(@Body req: RegisterReq): Observable<BaseResp<String>>
}