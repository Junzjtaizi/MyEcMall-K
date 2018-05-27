package cn.nieking.provider.common

import cn.nieking.baselibrary.common.BaseConstant
import cn.nieking.baselibrary.utils.AppPrefsUtils

fun isLogined(): Boolean {
    return AppPrefsUtils.getString(BaseConstant.KEY_SP_TOKEN).isNotEmpty()
}