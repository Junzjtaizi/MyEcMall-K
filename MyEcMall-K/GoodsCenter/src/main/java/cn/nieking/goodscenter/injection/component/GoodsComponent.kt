package cn.nieking.goodscenter.injection.component

import cn.nieking.baselibrary.injection.PerComponentScope
import cn.nieking.baselibrary.injection.component.ActivityComponent
import cn.nieking.goodscenter.injection.module.CartModule
import cn.nieking.goodscenter.injection.module.GoodsModule
import cn.nieking.goodscenter.ui.activity.GoodsActivity
import cn.nieking.goodscenter.ui.fragment.GoodsDetailTabOneFragment
import dagger.Component

@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class),
        modules = arrayOf(GoodsModule::class, CartModule::class))
interface GoodsComponent {

    fun inject(activity: GoodsActivity)
    fun inject(fragment: GoodsDetailTabOneFragment)
}