package cn.nieking.usercenter.data.api

import cn.nieking.baselibrary.data.protocol.BaseResp
import cn.nieking.usercenter.data.protocol.*
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

interface UserApi {
    @POST("userCenter/register")
    fun register(@Body req: RegisterReq): Observable<BaseResp<String>>

    @POST("userCenter/login")
    fun login(@Body req: LoginReq): Observable<BaseResp<UserInfo>>

    @POST("userCenter/forgetPwd")
    fun forgetPwd(@Body req: ForgetPwdReq): Observable<BaseResp<String>>

    @POST("userCenter/resetPwd")
    fun resetPwd(@Body req: ResetPwdReq): Observable<BaseResp<String>>
}