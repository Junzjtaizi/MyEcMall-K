package cn.nieking.myecmallk.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.nieking.baselibrary.ext.loadUrl
import cn.nieking.baselibrary.ext.onClick
import cn.nieking.baselibrary.ui.fragment.BaseFragment
import cn.nieking.baselibrary.utils.AppPrefsUtils
import cn.nieking.myecmallk.R
import cn.nieking.myecmallk.ui.activity.SettingActivity
import cn.nieking.ordercenter.common.OrderConstant
import cn.nieking.ordercenter.common.OrderStatus
import cn.nieking.ordercenter.ui.activity.OrderActivity
import cn.nieking.ordercenter.ui.activity.ShipAddressActivity
import cn.nieking.provider.common.ProviderConstant
import cn.nieking.provider.common.afterLogin
import cn.nieking.provider.common.isLogined
import cn.nieking.usercenter.ui.activity.UserInfoActivity
import kotlinx.android.synthetic.main.fragment_me.*
import org.jetbrains.anko.startActivity

class MeFragment : BaseFragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater?.inflate(R.layout.fragment_me, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun initView() {
        mUserIconIv.onClick(this)
        mUserNameTv.onClick(this)

        mSettingTv.onClick(this)
        mAddressTv.onClick(this)

        mAllOrderTv.onClick(this)
        mWaitPayOrderTv.onClick(this)
        mWaitConfirmOrderTv.onClick(this)
        mCompleteOrderTv.onClick(this)
    }

    private fun loadData() {
        if (isLogined()) {
            val userIcon = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_ICON)
            if (userIcon.isNotEmpty()) {
                mUserIconIv.loadUrl(userIcon)
            }
            mUserNameTv.text = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_NAME)
        } else {
            mUserIconIv.setImageResource(R.drawable.icon_default_user)
            mUserNameTv.text = getString(R.string.un_login_text)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mUserIconIv, R.id.mUserNameTv -> {
                afterLogin {
                    startActivity<UserInfoActivity>()
                }
            }
            R.id.mSettingTv -> {
                startActivity<SettingActivity>()
            }
            R.id.mAddressTv -> {
                startActivity<ShipAddressActivity>()
            }
            R.id.mAllOrderTv -> {
                afterLogin { startActivity<OrderActivity>() }
            }
            R.id.mWaitPayOrderTv -> {
                startActivity<OrderActivity>(OrderConstant.KEY_ORDER_STATUS to OrderStatus.ORDER_WAIT_PAY)
            }
            R.id.mWaitConfirmOrderTv -> {
                startActivity<OrderActivity>(OrderConstant.KEY_ORDER_STATUS to OrderStatus.ORDER_WAIT_CONFIRM)
            }
            R.id.mCompleteOrderTv -> {
                startActivity<OrderActivity>(OrderConstant.KEY_ORDER_STATUS to OrderStatus.ORDER_COMPLETED)
            }
        }
    }
}