package cn.nieking.usercenter.service.impl

import cn.nieking.baselibrary.data.protocol.BaseResp
import cn.nieking.baselibrary.rx.BaseException
import cn.nieking.usercenter.data.repository.UserRepository
import cn.nieking.usercenter.service.UserService
import rx.Observable
import rx.functions.Func1

class UserServiceImpl : UserService {
    override fun register(mobile: String, pwd: String, verifyCode: String): Observable<Boolean> {
        val repository = UserRepository()
        return repository
                .reigster(mobile, pwd, verifyCode)
                .flatMap(Func1<BaseResp<String>, Observable<Boolean>> { t ->
                    if (t.status != 0) {
                        return@Func1 Observable.error(BaseException(t.status, t.message))
                    }
                    Observable.just(true)
                })
    }
}