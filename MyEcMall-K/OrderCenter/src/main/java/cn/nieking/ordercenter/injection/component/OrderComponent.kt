package cn.nieking.ordercenter.injection.component

import cn.nieking.baselibrary.injection.PerComponentScope
import cn.nieking.baselibrary.injection.component.ActivityComponent
import cn.nieking.ordercenter.injection.module.OrderModule
import cn.nieking.ordercenter.ui.activity.OrderConfirmActivity
import dagger.Component

@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class),
        modules = arrayOf(OrderModule::class))
interface OrderComponent {

    fun inject(activity: OrderConfirmActivity)
}