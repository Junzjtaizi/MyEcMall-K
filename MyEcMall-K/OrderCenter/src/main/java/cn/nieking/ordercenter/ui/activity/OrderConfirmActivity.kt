package cn.nieking.ordercenter.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import cn.nieking.baselibrary.ext.onClick
import cn.nieking.baselibrary.ext.setVisible
import cn.nieking.baselibrary.ui.activity.BaseMvpActivity
import cn.nieking.baselibrary.utils.YuanFenConverter
import cn.nieking.ordercenter.R
import cn.nieking.ordercenter.data.protocol.Order
import cn.nieking.ordercenter.event.SelectAddressEvent
import cn.nieking.ordercenter.injection.component.DaggerOrderComponent
import cn.nieking.ordercenter.injection.module.OrderModule
import cn.nieking.ordercenter.presenter.OrderConfirmPresenter
import cn.nieking.ordercenter.presenter.view.OrderConfirmView
import cn.nieking.ordercenter.ui.adapter.OrderGoodsAdapter
import cn.nieking.provider.common.ProviderConstant
import cn.nieking.provider.router.RouterPath
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import kotlinx.android.synthetic.main.activity_order_confirm.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

@Route(path = RouterPath.OrderCenter.PATH_ORDER_CONFIRM)
class OrderConfirmActivity : BaseMvpActivity<OrderConfirmPresenter>(), OrderConfirmView {

    @Autowired(name = ProviderConstant.KEY_ORDER_ID)
    @JvmField
    var mOrderId: Int = 0

    private lateinit var mAdapter: OrderGoodsAdapter
    private var mOrder: Order? = null

    override fun injectComponent() {
        DaggerOrderComponent.builder()
                .activityComponent(mActivityComponent)
                .orderModule(OrderModule())
                .build()
                .inject(this)
        mPresenter.mView = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_confirm)

        initView()
        initObserve()
        loadData()
    }

    override fun onDestroy() {
        Bus.unregister(this)
        super.onDestroy()
    }

    private fun initView() {

        mShipView.onClick {
            startActivity<ShipAddressActivity>()
        }

        mSelectShipTv.onClick {
            startActivity<ShipAddressActivity>()
        }

        mSubmitOrderBtn.onClick {
            mOrder?.let { mPresenter.submitOrder(it) }
        }

        mOrderGoodsRv.layoutManager = LinearLayoutManager(this)
        mAdapter = OrderGoodsAdapter(this)
        mOrderGoodsRv.adapter = mAdapter
    }

    private fun initObserve() {
        Bus.observe<SelectAddressEvent>()
                .subscribe {
                    mOrder?.let { i ->
                        i.shipAddress = it.address
                    }
                    updateAddressView()
                }.registerInBus(this)
    }

    private fun loadData() {
        mPresenter.getOrderById(mOrderId)
    }

    private fun updateAddressView() {
        mOrder?.let {
            if (it.shipAddress == null) {
                mSelectShipTv.setVisible(true)
                mShipView.setVisible(false)
            } else {
                mSelectShipTv.setVisible(false)
                mShipView.setVisible(true)

                mShipNameTv.text = "${it.shipAddress!!.shipUserName} ${it.shipAddress!!.shipUserMobile}"
                mShipAddressTv.text = it.shipAddress!!.shipAddress
            }
        }
    }

    override fun onGetOrderByIdResult(result: Order) {
        mOrder = result
        mAdapter.setData(result.orderGoodsList)
        mTotalPriceTv.text = "合计：${YuanFenConverter.changeF2YWithUnit(result.totalPrice)}"

        updateAddressView()
    }

    override fun onSubmitOrderResult(result: Boolean) {
        toast("提交成功！")
    }
}