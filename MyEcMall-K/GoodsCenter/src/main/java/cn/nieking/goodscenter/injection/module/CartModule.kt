package cn.nieking.goodscenter.injection.module

import cn.nieking.goodscenter.service.CartService
import cn.nieking.goodscenter.service.impl.CartServiceImpl
import dagger.Module
import dagger.Provides

/*
    购物车Module
 */
@Module
class CartModule {

    @Provides
    fun provideCartservice(cartService: CartServiceImpl): CartService {
        return cartService
    }

}
