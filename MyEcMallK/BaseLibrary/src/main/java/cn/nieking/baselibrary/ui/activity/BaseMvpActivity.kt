package cn.nieking.baselibrary.ui.activity

import android.os.Bundle
import cn.nieking.baselibrary.common.BaseApplication
import cn.nieking.baselibrary.injection.component.ActivityComponent
import cn.nieking.baselibrary.injection.component.DaggerActivityComponent
import cn.nieking.baselibrary.injection.module.ActivityModule
import cn.nieking.baselibrary.injection.module.LifecycleProviderModule
import cn.nieking.baselibrary.presenter.BasePresenter
import cn.nieking.baselibrary.presenter.view.BaseView
import javax.inject.Inject

abstract class BaseMvpActivity<T : BasePresenter<*>> : BaseActivity(), BaseView {
    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun onError() {

    }

    @Inject
    lateinit var mPresenter: T
    lateinit var activityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActivityInjection()
        injectComponent()
    }

    abstract fun injectComponent()

    private fun initActivityInjection() {
        activityComponent = DaggerActivityComponent.builder()
                .appComponent((application as BaseApplication).appComponent)
                .activityModule(ActivityModule(this))
                .lifecycleProviderModule(LifecycleProviderModule(this))
                .build()
    }
}