package cn.nieking.paysdk.ui.activity

import android.os.Bundle
import android.view.View
import cn.nieking.baselibrary.ext.onClick
import cn.nieking.baselibrary.ui.activity.BaseMvpActivity
import cn.nieking.baselibrary.utils.YuanFenConverter
import cn.nieking.paysdk.R
import cn.nieking.paysdk.injection.component.DaggerPayComponent
import cn.nieking.paysdk.injection.module.PayModule
import cn.nieking.paysdk.presenter.PayPresenter
import cn.nieking.paysdk.presenter.view.PayView
import cn.nieking.provider.common.ProviderConstant
import cn.nieking.provider.router.RouterPath
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.alipay.sdk.app.EnvUtils
import com.alipay.sdk.app.PayTask
import kotlinx.android.synthetic.main.activity_cash_register.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

@Route(path = RouterPath.PaySDK.PATH_PAY)
class CashRegisterActivity : BaseMvpActivity<PayPresenter>(), PayView, View.OnClickListener {

    override fun injectComponent() {
        DaggerPayComponent.builder()
                .activityComponent(mActivityComponent)
                .payModule(PayModule())
                .build()
                .inject(this)
        mPresenter.mView = this
    }

    @Autowired(name = ProviderConstant.KEY_ORDER_ID)
    @JvmField
    var mOrderId: Int = 0

    @Autowired(name = ProviderConstant.KEY_ORDER_PRICE)
    @JvmField
    var mTotalPrice: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cash_register)

        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX)

        ARouter.getInstance().inject(this)

        initView()
    }

    private fun initView() {
        mAlipayTypeTv.isSelected = true
        mTotalPriceTv.text = YuanFenConverter.changeF2YWithUnit(mTotalPrice)

        mAlipayTypeTv.onClick(this)
        mWeixinTypeTv.onClick(this)
        mBankCardTypeTv.onClick(this)

        mPayBtn.onClick(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mPayBtn -> { mPresenter.getPaySign(mOrderId, mTotalPrice) }
            R.id.mAlipayTypeTv -> { updatePayType(true, false, false) }
            R.id.mWeixinTypeTv -> { updatePayType(false, true, false) }
            R.id.mBankCardTypeTv -> { updatePayType(false, false, true) }
        }
    }

    private fun updatePayType(isAliPay: Boolean, isWXPay: Boolean, isBankCardPay: Boolean) {
        mAlipayTypeTv.isSelected = isAliPay
        mWeixinTypeTv.isSelected = isWXPay
        mBankCardTypeTv.isSelected = isBankCardPay
    }

    override fun onGetPaySignResult(result: String) {
        doAsync {
            val resultMap: Map<String, String> = PayTask(this@CashRegisterActivity).payV2(result, true)
            uiThread {
                if (resultMap["resultStatus"].equals("9000")) {
                    mPresenter.payOrder(mOrderId)
                } else {
                    toast("支付失败${resultMap["memo"]}")
                }
            }
        }
    }

    override fun onPayOrderResult(result: Boolean) {
        toast("支付成功")
        finish()
    }
}