package cn.nieking.baselibrary.common

import android.app.Application
import cn.nieking.baselibrary.injection.component.AppComponent
import cn.nieking.baselibrary.injection.component.DaggerAppComponent
import cn.nieking.baselibrary.injection.module.AppModule

class BaseApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        initAppInjection()
    }

    private fun initAppInjection() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}