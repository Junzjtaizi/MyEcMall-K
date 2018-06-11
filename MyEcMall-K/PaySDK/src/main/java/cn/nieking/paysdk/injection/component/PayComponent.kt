package cn.nieking.paysdk.injection.component

import cn.nieking.baselibrary.injection.PerComponentScope
import cn.nieking.baselibrary.injection.component.ActivityComponent
import cn.nieking.paysdk.injection.module.PayModule
import cn.nieking.paysdk.ui.activity.CashRegisterActivity
import dagger.Component

@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class),
        modules = arrayOf(PayModule::class))
interface PayComponent {

    fun inject(activity: CashRegisterActivity)
}