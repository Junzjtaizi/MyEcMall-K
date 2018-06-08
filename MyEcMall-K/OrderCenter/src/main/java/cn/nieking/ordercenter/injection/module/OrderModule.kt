package cn.nieking.ordercenter.injection.module

import cn.nieking.ordercenter.service.OrderService
import cn.nieking.ordercenter.service.impl.OrderServiceImpl
import dagger.Module
import dagger.Provides

@Module
class OrderModule {

    @Provides
    fun provideOrderService(service: OrderServiceImpl): OrderService {
        return service
    }
}