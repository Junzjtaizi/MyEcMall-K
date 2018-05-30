package cn.nieking.usercenter.ui.activity

import android.os.Bundle
import android.view.View
import cn.nieking.baselibrary.ext.enable
import cn.nieking.baselibrary.ext.onClick
import cn.nieking.baselibrary.ui.activity.BaseMvpActivity
import cn.nieking.usercenter.R
import cn.nieking.usercenter.data.protocol.UserInfo
import cn.nieking.usercenter.injection.component.DaggerUserComponent
import cn.nieking.usercenter.injection.module.UserModule
import cn.nieking.usercenter.presenter.LoginPresenter
import cn.nieking.usercenter.presenter.view.LoginView
import cn.nieking.usercenter.utils.UserPrefsUtils
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class LoginActivity : BaseMvpActivity<LoginPresenter>(), LoginView, View.OnClickListener {

    override fun injectComponent() {
        DaggerUserComponent.builder()
                .activityComponent(mActivityComponent)
                .userModule(UserModule())
                .build()
                .inject(this)
        mPresenter.mView = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView()
    }

    /**
     * 初始化视图
     */
    private fun initView() {
        mLoginBtn.enable(mMobileEt, { isBtnEnable() })
        mLoginBtn.enable(mPwdEt, { isBtnEnable() })
        mLoginBtn.onClick(this)
        mForgetPwdTv.onClick(this)
        mHeaderBar.getRightView().onClick(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mLoginBtn -> {
                mPresenter.login(
                        mMobileEt.text.toString(),
                        mPwdEt.text.toString(),
                        "")
            }
            R.id.mRightTv -> {
                startActivity<RegisterActivity>()
            }
            R.id.mForgetPwdTv -> {
                startActivity<ForgetPwdActivity>()
            }
        }
    }

    /**
     * 判断按钮是否可点击
     */
    private fun isBtnEnable(): Boolean {
        return mMobileEt.text.isNullOrEmpty().not() &&
                mPwdEt.text.isNullOrEmpty().not()
    }

    override fun onLoginResult(result: UserInfo) {
        toast("登录成功")
        UserPrefsUtils.putUserInfo(result)
        finish()
    }
}
