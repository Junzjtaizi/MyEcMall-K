package cn.nieking.usercenter.injection.component

import cn.nieking.baselibrary.injection.PerComponentScope
import cn.nieking.baselibrary.injection.component.ActivityComponent
import cn.nieking.usercenter.injection.module.UserModule
import cn.nieking.usercenter.ui.activity.*
import dagger.Component

@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class), modules = arrayOf(UserModule::class))
interface UserComponent {

    fun inject(activity: RegisterActivity)
    fun inject(activity: LoginActivity)
    fun inject(activity: ForgetPwdActivity)
    fun inject(activity: ResetPwdActivity)
    fun inject(activity: UserInfoActivity)
}