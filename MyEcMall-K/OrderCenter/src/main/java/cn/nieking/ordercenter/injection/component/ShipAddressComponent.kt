package cn.nieking.ordercenter.injection.component

import cn.nieking.baselibrary.injection.PerComponentScope
import cn.nieking.baselibrary.injection.component.ActivityComponent
import cn.nieking.ordercenter.injection.module.ShipAddressModule
import cn.nieking.ordercenter.ui.activity.ShipAddressActivity
import cn.nieking.ordercenter.ui.activity.ShipAddressEditActivity
import dagger.Component

@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class),
        modules = arrayOf(ShipAddressModule::class))
interface ShipAddressComponent {

    fun inject(activity: ShipAddressEditActivity)
    fun inject(activity: ShipAddressActivity)
}