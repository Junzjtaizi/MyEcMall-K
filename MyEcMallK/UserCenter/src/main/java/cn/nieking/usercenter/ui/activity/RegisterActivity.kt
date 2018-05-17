package cn.nieking.usercenter.ui.activity

import android.os.Bundle
import cn.nieking.baselibrary.common.AppManager
import cn.nieking.baselibrary.ext.onClick
import cn.nieking.baselibrary.ui.activity.BaseMvpActivity
import cn.nieking.usercenter.R
import cn.nieking.usercenter.injection.component.DaggerUserComponent
import cn.nieking.usercenter.injection.module.UserModule
import cn.nieking.usercenter.presenter.RegisterPresenter
import cn.nieking.usercenter.presenter.view.RegisterView
import com.kotlin.base.widgets.VerifyButton
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast

class RegisterActivity : BaseMvpActivity<RegisterPresenter>(), RegisterView {

    private var pressTime: Long = 0

    override fun injectComponent() {
        DaggerUserComponent.builder()
                .activityComponent(activityComponent)
                .userModule(UserModule())
                .build()
                .inject(this)
        mPresenter.mView = this
    }

    override fun onRegisterResult(result: String) {
        toast(result)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mRegisterBtn.onClick {
            mPresenter.register(
                    mMobileEt.text.toString(),
                    mVerifyCodeEt.text.toString(),
                    mPwdEt.text.toString())
        }

        mGetVerifyCodeBtn.onClick {
            mGetVerifyCodeBtn.requestSendVerifyNumber()
        }
    }

    override fun onBackPressed() {
        val time = System.currentTimeMillis()
        if (time - pressTime >= 2000) {
            toast("再按一次退出")
            pressTime = time
        } else {
            AppManager.instance.exitApp(this)
        }
    }
}
