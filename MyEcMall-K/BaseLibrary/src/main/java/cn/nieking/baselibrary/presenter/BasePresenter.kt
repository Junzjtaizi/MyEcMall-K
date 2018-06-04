package cn.nieking.baselibrary.presenter

import android.content.Context
import cn.nieking.baselibrary.presenter.view.BaseView
import cn.nieking.baselibrary.utils.NetWorkUtils
import com.trello.rxlifecycle.LifecycleProvider
import javax.inject.Inject

open class BasePresenter<T: BaseView> {

    lateinit var mView: T

    @Inject
    lateinit var lifecycleProvider: LifecycleProvider<*>
    @Inject
    lateinit var context: Context

    fun checkNetWork(): Boolean {
        return if (NetWorkUtils.isNetWorkAvailable(context)) {
            true
        } else {
            mView.onError("网络连接已断开！")
            false
        }
    }
}