package cn.nieking.goodscenter.injection.component

import cn.nieking.baselibrary.injection.PerComponentScope
import cn.nieking.baselibrary.injection.component.ActivityComponent
import cn.nieking.goodscenter.injection.module.CartModule
import cn.nieking.goodscenter.ui.fragment.CartFragment
import dagger.Component

/*
    购物车Component
 */
@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class),modules = arrayOf(CartModule::class))
interface CartComponent {
    fun inject(fragment: CartFragment)
}
