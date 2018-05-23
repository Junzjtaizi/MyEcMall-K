package cn.nieking.usercenter.data.protocol

/*
    修改用户资料请求体
 */
data class EditUserReq(val userIcon: String, val userName: String, val gender: String, val sign: String)
