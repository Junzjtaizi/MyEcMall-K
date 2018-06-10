package cn.nieking.ordercenter.injection.component

import cn.nieking.baselibrary.injection.PerComponentScope
import cn.nieking.baselibrary.injection.component.ActivityComponent
import cn.nieking.ordercenter.injection.module.OrderModule
import cn.nieking.ordercenter.ui.activity.OrderConfirmActivity
import cn.nieking.ordercenter.ui.activity.OrderDetailActivity
import cn.nieking.ordercenter.ui.fragment.OrderFragment
import dagger.Component

@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class),
        modules = arrayOf(OrderModule::class))
interface OrderComponent {

    fun inject(activity: OrderConfirmActivity)
    fun inject(fragment: OrderFragment)
    fun inject(activity: OrderDetailActivity)
}