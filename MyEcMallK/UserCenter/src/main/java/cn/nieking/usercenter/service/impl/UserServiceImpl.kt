package cn.nieking.usercenter.service.impl

import cn.nieking.baselibrary.ext.convert
import cn.nieking.baselibrary.ext.convertBoolean
import cn.nieking.usercenter.data.protocol.UserInfo
import cn.nieking.usercenter.data.repository.UserRepository
import cn.nieking.usercenter.service.UserService
import rx.Observable
import javax.inject.Inject

class UserServiceImpl @Inject constructor() : UserService {

    @Inject
    lateinit var repository: UserRepository

    override fun register(mobile: String, pwd: String, verifyCode: String): Observable<Boolean> {
        return repository
                .register(mobile, pwd, verifyCode)
                .convertBoolean()
    }

    override fun login(mobile: String, pwd: String, pushId: String): Observable<UserInfo> {
        return repository
                .login(mobile, pwd, pushId)
                .convert()
    }

    override fun forgetPwd(mobile: String, verifyCode: String): Observable<Boolean> {
        return repository
                .forgetPwd(mobile, verifyCode)
                .convertBoolean()
    }

    override fun resetPwd(mobile: String, pwd: String): Observable<Boolean> {
        return repository
                .resetPwd(mobile, pwd)
                .convertBoolean()
    }

    override fun editUser(userIcon: String, userName: String, userGender: String, userSign: String): Observable<UserInfo> {
        return repository
                .editUser(userIcon, userName, userGender, userSign)
                .convert()
    }
}