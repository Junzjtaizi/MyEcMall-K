package cn.nieking.ordercenter.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import cn.nieking.baselibrary.ext.onClick
import cn.nieking.baselibrary.ui.activity.BaseMvpActivity
import cn.nieking.baselibrary.utils.YuanFenConverter
import cn.nieking.ordercenter.R
import cn.nieking.ordercenter.data.protocol.Order
import cn.nieking.ordercenter.injection.component.DaggerOrderComponent
import cn.nieking.ordercenter.injection.module.OrderModule
import cn.nieking.ordercenter.presenter.OrderConfirmPresenter
import cn.nieking.ordercenter.presenter.view.OrderConfirmView
import cn.nieking.ordercenter.ui.adapter.OrderGoodsAdapter
import cn.nieking.provider.common.ProviderConstant
import cn.nieking.provider.router.RouterPath
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.activity_order_confirm.*
import org.jetbrains.anko.startActivity

@Route(path = RouterPath.OrderCenter.PATH_ORDER_CONFIRM)
class OrderConfirmActivity : BaseMvpActivity<OrderConfirmPresenter>(), OrderConfirmView {

    @Autowired(name = ProviderConstant.KEY_ORDER_ID)
    @JvmField
    var mOrderId: Int = 0

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
        setContentView(R.layout.activity_order_confirm)

        initView()
        loadData()
    }

    private fun initView() {
        mSelectShipTv.onClick {
            startActivity<ShipAddressActivity>()
        }

        mOrderGoodsRv.layoutManager = LinearLayoutManager(this)
        mAdapter = OrderGoodsAdapter(this)
        mOrderGoodsRv.adapter = mAdapter
    }

    private fun loadData() {
        mPresenter.getOrderById(mOrderId)
    }

    override fun onGetOrderByIdResult(result: Order) {
        mAdapter.setData(result.orderGoodsList)
        mTotalPriceTv.text = "合计：${YuanFenConverter.changeF2YWithUnit(result.totalPrice)}"
    }
}