package cn.nieking.goodscenter.injection.module

import cn.nieking.goodscenter.service.GoodsService
import cn.nieking.goodscenter.service.impl.GoodsServiceImpl
import dagger.Module
import dagger.Provides

@Module
class GoodsModule {

    @Provides
    fun providesGoodsService(service: GoodsServiceImpl): GoodsService {
        return service
    }
}