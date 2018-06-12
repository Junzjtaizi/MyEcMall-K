package cn.nieking.myecmallk.common

import cn.jpush.android.api.JPushInterface
import cn.nieking.baselibrary.common.BaseApplication

class MainApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()

        JPushInterface.setDebugMode(true)
        JPushInterface.init(this)
    }
}