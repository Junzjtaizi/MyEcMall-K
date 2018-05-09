package cn.nieking.usercenter.injection.component

import cn.nieking.usercenter.injection.module.UserModule
import cn.nieking.usercenter.ui.activity.RegisterActivity
import dagger.Component

@Component(modules = arrayOf(UserModule::class))
interface UserComponent {

    fun inject(activity: RegisterActivity)
}