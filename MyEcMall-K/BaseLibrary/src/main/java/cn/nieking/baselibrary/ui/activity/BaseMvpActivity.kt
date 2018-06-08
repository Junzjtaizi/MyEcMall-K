package cn.nieking.baselibrary.ui.activity

import android.os.Bundle
import cn.nieking.baselibrary.common.BaseApplication
import cn.nieking.baselibrary.injection.component.ActivityComponent
import cn.nieking.baselibrary.injection.component.DaggerActivityComponent
import cn.nieking.baselibrary.injection.module.ActivityModule
import cn.nieking.baselibrary.injection.module.LifecycleProviderModule
import cn.nieking.baselibrary.presenter.BasePresenter
import cn.nieking.baselibrary.presenter.view.BaseView
import cn.nieking.baselibrary.widgets.ProgressLoading
import com.alibaba.android.arouter.launcher.ARouter
import org.jetbrains.anko.toast
import javax.inject.Inject

abstract class BaseMvpActivity<T : BasePresenter<*>> : BaseActivity(), BaseView {

    @Inject
    lateinit var mPresenter: T
    lateinit var mActivityComponent: ActivityComponent

    lateinit var mLoadingDialog: ProgressLoading

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActivityInjection()
        injectComponent()
        mLoadingDialog = ProgressLoading.create(this)
        ARouter.getInstance().inject(this)
    }

    private fun initActivityInjection() {
        mActivityComponent = DaggerActivityComponent.builder()
                .appComponent((application as BaseApplication).appComponent)
                .activityModule(ActivityModule(this))
                .lifecycleProviderModule(LifecycleProviderModule(this))
                .build()
    }

    override fun showLoading() {
        mLoadingDialog.showLoading()
    }

    override fun hideLoading() {
        mLoadingDialog.hideLoading()
    }

    override fun onError(text: String) {
        toast(text)
    }

    abstract fun injectComponent()
}