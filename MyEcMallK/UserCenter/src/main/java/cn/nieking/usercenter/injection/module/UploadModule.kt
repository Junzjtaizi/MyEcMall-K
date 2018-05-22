package cn.nieking.usercenter.injection.module

import cn.nieking.usercenter.service.UploadService
import cn.nieking.usercenter.service.impl.UploadServiceImpl
import dagger.Module
import dagger.Provides

@Module
class UploadModule {

    @Provides
    fun providesUserService(uploadService: UploadServiceImpl): UploadService {
        return uploadService
    }
}