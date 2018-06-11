package cn.nieking.ordercenter.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.nieking.baselibrary.ext.startLoading
import cn.nieking.baselibrary.ui.fragment.BaseMvpFragment
import cn.nieking.ordercenter.R
import cn.nieking.ordercenter.common.OrderConstant
import cn.nieking.ordercenter.data.protocol.Order
import cn.nieking.ordercenter.injection.component.DaggerOrderComponent
import cn.nieking.ordercenter.injection.module.OrderModule
import cn.nieking.ordercenter.presenter.OrderListPresenter
import cn.nieking.ordercenter.presenter.view.OrderListView
import cn.nieking.ordercenter.ui.activity.OrderDetailActivity
import cn.nieking.ordercenter.ui.adapter.OrderAdapter
import cn.nieking.provider.common.ProviderConstant
import cn.nieking.provider.router.RouterPath
import com.alibaba.android.arouter.launcher.ARouter
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.kennyc.view.MultiStateView
import kotlinx.android.synthetic.main.fragment_order.*
import org.jetbrains.anko.toast
import org.jetbrains.anko.startActivity

class OrderFragment : BaseMvpFragment<OrderListPresenter>(), OrderListView {

    private lateinit var mAdapter: OrderAdapter

    override fun injectComponent() {
        DaggerOrderComponent.builder()
                .activityComponent(mActivityComponent)
                .orderModule(OrderModule())
                .build()
                .inject(this)
        mPresenter.mView = this
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_order, container, false)
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
        mOrderRv.layoutManager = LinearLayoutManager(activity)
        mAdapter = OrderAdapter(activity)
        mOrderRv.adapter = mAdapter

        mAdapter.listener = object : OrderAdapter.OnOptClickListener {
            override fun onOptClick(optType: Int, order: Order) {
                when (optType) {
                    OrderConstant.OPT_ORDER_PAY -> {
                        ARouter.getInstance().build(RouterPath.PaySDK.PATH_PAY)
                                .withInt(ProviderConstant.KEY_ORDER_ID, order.id)
                                .withLong(ProviderConstant.KEY_ORDER_PRICE, order.totalPrice)
                                .navigation()
                    }
                    OrderConstant.OPT_ORDER_CONFIRM -> {
                        mPresenter.confirmOrder(order.id)
                    }
                    OrderConstant.OPT_ORDER_CANCEL -> {
                        showCancelDialog(order)
                    }
                }
            }
        }

        mAdapter.onItemClick { item, _ ->
            startActivity<OrderDetailActivity>(ProviderConstant.KEY_ORDER_ID to item.id)
        }
    }

    private fun loadData() {
        mMultiStateView.startLoading()
        mPresenter.getOrderList(arguments.getInt(OrderConstant.KEY_ORDER_STATUS, -1))
    }

    private fun showCancelDialog(order: Order) {
        AlertView(
                "取消订单",
                "确定取消该订单？",
                "取消",
                null,
                arrayOf("确定"),
                activity,
                AlertView.Style.Alert,
                OnItemClickListener { _, position ->
                    if (position == 0) {
                        mPresenter.cancelOrder(order.id)
                    }
                }).show()
    }

    override fun onGetOrderListResult(result: MutableList<Order>?) {
        if (result != null && result.size > 0) {
            mAdapter.setData(result)
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        } else {
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
    }

    override fun onConfirmOrderResult(result: Boolean) {
        toast("确认收货成功！")
        loadData()
    }

    override fun onCancelOrderResult(result: Boolean) {
        toast("取消订单成功！")
        loadData()
    }
}