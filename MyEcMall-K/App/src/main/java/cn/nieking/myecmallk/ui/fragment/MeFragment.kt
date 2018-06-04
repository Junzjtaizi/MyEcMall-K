package cn.nieking.myecmallk.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.nieking.baselibrary.common.BaseConstant
import cn.nieking.baselibrary.ext.loadUrl
import cn.nieking.baselibrary.ext.onClick
import cn.nieking.baselibrary.ui.fragment.BaseFragment
import cn.nieking.baselibrary.utils.AppPrefsUtils
import cn.nieking.baselibrary.widgets.BannerImageLoader
import cn.nieking.myecmallk.R
import cn.nieking.myecmallk.common.*
import cn.nieking.myecmallk.ui.activity.SettingActivity
import cn.nieking.myecmallk.ui.adapter.HomeDiscountAdapter
import cn.nieking.myecmallk.ui.adapter.TopicAdapter
import cn.nieking.provider.common.ProviderConstant
import cn.nieking.provider.common.isLogined
import cn.nieking.usercenter.ui.activity.LoginActivity
import cn.nieking.usercenter.ui.activity.UserInfoActivity
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_me.*
import me.crosswall.lib.coverflow.CoverFlow
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
                if (isLogined()) {
                    startActivity<UserInfoActivity>()
                } else {
                    startActivity<LoginActivity>()
                }
            }
            R.id.mSettingTv -> { startActivity<SettingActivity>() }
        }
    }
}