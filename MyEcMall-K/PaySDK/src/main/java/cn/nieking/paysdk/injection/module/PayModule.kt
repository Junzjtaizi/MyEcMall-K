package cn.nieking.paysdk.injection.module

import cn.nieking.paysdk.service.PayService
import cn.nieking.paysdk.service.impl.PayServiceImpl
import dagger.Module
import dagger.Provides

@Module
class PayModule {

    @Provides
    fun providePayService(service: PayServiceImpl): PayService {
        return service
    }
}