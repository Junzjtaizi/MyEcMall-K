package cn.nieking.goodscenter.injection.component

import cn.nieking.baselibrary.injection.PerComponentScope
import cn.nieking.baselibrary.injection.component.ActivityComponent
import cn.nieking.goodscenter.injection.module.CategoryModule
import cn.nieking.goodscenter.ui.fragment.CategoryFragment
import dagger.Component

@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class),
        modules = arrayOf(CategoryModule::class))
interface CategoryComponent {

    fun inject(fragment: CategoryFragment)
}