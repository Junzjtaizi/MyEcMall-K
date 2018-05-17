package cn.nieking.baselibrary.common

import android.app.Application
import android.content.Context
import cn.nieking.baselibrary.injection.component.AppComponent
import cn.nieking.baselibrary.injection.component.DaggerAppComponent
import cn.nieking.baselibrary.injection.module.AppModule

class BaseApplication : Application() {

    lateinit var appComponent: AppComponent

    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()

        initAppInjection()
        context = this
    }

    private fun initAppInjection() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}