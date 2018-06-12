package cn.nieking.messagecenter.injection.component

import cn.nieking.baselibrary.injection.PerComponentScope
import cn.nieking.baselibrary.injection.component.ActivityComponent
import cn.nieking.messagecenter.injection.module.MessageModule
import cn.nieking.messagecenter.ui.fragment.MessageFragment
import dagger.Component

@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class),
        modules = arrayOf(MessageModule::class))
interface MessageComponent {

    fun inject(fragment: MessageFragment)
}