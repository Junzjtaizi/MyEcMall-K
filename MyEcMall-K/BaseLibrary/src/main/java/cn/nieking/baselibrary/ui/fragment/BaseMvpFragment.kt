package cn.nieking.baselibrary.ui.fragment

import android.os.Bundle
import cn.nieking.baselibrary.common.BaseApplication
import cn.nieking.baselibrary.injection.component.ActivityComponent
import cn.nieking.baselibrary.injection.component.DaggerActivityComponent
import cn.nieking.baselibrary.injection.module.ActivityModule
import cn.nieking.baselibrary.injection.module.LifecycleProviderModule
import cn.nieking.baselibrary.presenter.BasePresenter
import cn.nieking.baselibrary.presenter.view.BaseView
import org.jetbrains.anko.toast
import javax.inject.Inject

abstract class BaseMvpFragment<T : BasePresenter<*>> : BaseFragment(), BaseView {
    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun onError(text: String) {
        toast(text)
    }

    @Inject
    lateinit var mPresenter: T
    lateinit var mActivityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActivityInjection()
        injectComponent()
    }

    abstract fun injectComponent()

    private fun initActivityInjection() {
        mActivityComponent = DaggerActivityComponent.builder()
                .appComponent((activity.application as BaseApplication).appComponent)
                .activityModule(ActivityModule(activity))
                .lifecycleProviderModule(LifecycleProviderModule(this))
                .build()
    }
}