package cn.nieking.ordercenter.injection.module

import cn.nieking.ordercenter.service.ShipAddressService
import cn.nieking.ordercenter.service.impl.ShipAddressServiceImpl
import dagger.Module
import dagger.Provides

@Module
class ShipAddressModule {

    @Provides
    fun provideShipAddressService(service: ShipAddressServiceImpl): ShipAddressService {
        return service
    }
}