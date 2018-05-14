package cn.nieking.usercenter.service.impl

import cn.nieking.baselibrary.ext.convertBoolean
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
}