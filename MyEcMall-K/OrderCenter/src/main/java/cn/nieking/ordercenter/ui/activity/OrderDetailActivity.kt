package cn.nieking.ordercenter.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import cn.nieking.baselibrary.ui.activity.BaseMvpActivity
import cn.nieking.baselibrary.utils.YuanFenConverter
import cn.nieking.ordercenter.R
import cn.nieking.ordercenter.data.protocol.Order
import cn.nieking.ordercenter.injection.component.DaggerOrderComponent
import cn.nieking.ordercenter.injection.module.OrderModule
import cn.nieking.ordercenter.presenter.OrderDetailPresenter
import cn.nieking.ordercenter.presenter.view.OrderDetailView
import cn.nieking.ordercenter.ui.adapter.OrderGoodsAdapter
import cn.nieking.provider.common.ProviderConstant
import kotlinx.android.synthetic.main.activity_order_detail.*

class OrderDetailActivity : BaseMvpActivity<OrderDetailPresenter>(), OrderDetailView {

    private lateinit var mAdapter: OrderGoodsAdapter

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
        setContentView(R.layout.activity_order_detail)

        initView()
        loadData()
    }

    private fun initView() {
        mOrderGoodsRv.layoutManager = LinearLayoutManager(this)
        mAdapter = OrderGoodsAdapter(this)
        mOrderGoodsRv.adapter = mAdapter
    }

    private fun loadData() {
        mPresenter.getOrderById(intent.getIntExtra(ProviderConstant.KEY_ORDER_ID, -1))
    }

    override fun onGetOrderByIdResult(result: Order) {
        mShipNameTv.setContentText(result.shipAddress!!.shipUserName)
        mShipMobileTv.setContentText(result.shipAddress!!.shipUserMobile)
        mShipAddressTv.setContentText(result.shipAddress!!.shipAddress)
        mTotalPriceTv.setContentText(YuanFenConverter.changeF2YWithUnit(result.totalPrice))

        mAdapter.setData(result.orderGoodsList)
    }
}