package cn.nieking.provider.common

import cn.nieking.baselibrary.common.BaseConstant
import cn.nieking.baselibrary.utils.AppPrefsUtils
import cn.nieking.provider.router.RouterPath
import com.alibaba.android.arouter.launcher.ARouter

fun isLogined(): Boolean {
    return AppPrefsUtils.getString(BaseConstant.KEY_SP_TOKEN).isNotEmpty()
}

fun afterLogin(method: () -> Unit) {
    if (isLogined()) {
        method()
    } else {
        ARouter.getInstance().build(RouterPath.UserCenter.PATH_LOGIN).navigation()
    }
}