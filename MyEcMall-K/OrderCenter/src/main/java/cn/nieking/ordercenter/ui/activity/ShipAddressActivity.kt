package cn.nieking.ordercenter.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import cn.nieking.baselibrary.ext.onClick
import cn.nieking.baselibrary.ext.startLoading
import cn.nieking.baselibrary.ui.activity.BaseMvpActivity
import cn.nieking.ordercenter.R
import cn.nieking.ordercenter.common.OrderConstant
import cn.nieking.ordercenter.data.protocol.ShipAddress
import cn.nieking.ordercenter.injection.component.DaggerShipAddressComponent
import cn.nieking.ordercenter.injection.module.ShipAddressModule
import cn.nieking.ordercenter.presenter.ShipAddressPresenter
import cn.nieking.ordercenter.presenter.view.ShipAddressView
import cn.nieking.ordercenter.ui.adapter.ShipAddressAdapter
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.kennyc.view.MultiStateView
import kotlinx.android.synthetic.main.activity_address.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class ShipAddressActivity : BaseMvpActivity<ShipAddressPresenter>(), ShipAddressView {

    private lateinit var mAdapter: ShipAddressAdapter

    override fun injectComponent() {
        DaggerShipAddressComponent.builder()
                .activityComponent(mActivityComponent)
                .shipAddressModule(ShipAddressModule())
                .build()
                .inject(this)
        mPresenter.mView = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)

        initView()
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun initView() {
        mAddressRv.layoutManager = LinearLayoutManager(this)
        mAdapter = ShipAddressAdapter(this)
        mAddressRv.adapter = mAdapter

        mAdapter.mOptClickListener = object : ShipAddressAdapter.OnOptClickListener {
            override fun onSetDefault(address: ShipAddress) {
                mPresenter.setDefaultShipAddress(address)
            }

            override fun onEdit(address: ShipAddress) {
                startActivity<ShipAddressEditActivity>(OrderConstant.KEY_SHIP_ADDRESS to address)
            }

            override fun onDelete(address: ShipAddress) {
                showAlertDialog(address)
            }
        }

        mAddAddressBtn.onClick {
            startActivity<ShipAddressEditActivity>()
        }
    }

    private fun loadData() {
        mMultiStateView.startLoading()
        mPresenter.getShipAddressList()
    }

    private fun showAlertDialog(address: ShipAddress) {
        AlertView(
                "删除",
                "确定删除该地址？",
                "取消",
                null,
                arrayOf("确定"),
                this@ShipAddressActivity,
                AlertView.Style.Alert, 
                OnItemClickListener { _, position ->
                    if (position == 0) {
                        mPresenter.deleteShipAddress(address.id)
                    }
                }).show()
    }

    override fun onGetShipAddressListResult(result: MutableList<ShipAddress>?) {
        if (result != null && result.size > 0) {
            mAdapter.setData(result)
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        } else {
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
    }

    override fun onSetDefaultResult(result: Boolean) {
        toast("设置成功！")
        loadData()
    }

    override fun onDeleteShipAddressResult(result: Boolean) {
        toast("删除成功！")
        loadData()
    }
}