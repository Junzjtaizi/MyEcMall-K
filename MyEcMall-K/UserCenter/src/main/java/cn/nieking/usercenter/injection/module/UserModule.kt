package cn.nieking.usercenter.injection.module

import cn.nieking.usercenter.service.UserService
import cn.nieking.usercenter.service.impl.UserServiceImpl
import dagger.Module
import dagger.Provides

@Module
class UserModule {

    @Provides
    fun providesUserService(service: UserServiceImpl): UserService {
        return service
    }
}