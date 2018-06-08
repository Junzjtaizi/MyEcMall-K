package cn.nieking.ordercenter.ui.activity

import android.os.Bundle
import cn.nieking.baselibrary.ext.onClick
import cn.nieking.baselibrary.ui.activity.BaseMvpActivity
import cn.nieking.ordercenter.R
import cn.nieking.ordercenter.common.OrderConstant
import cn.nieking.ordercenter.data.protocol.ShipAddress
import cn.nieking.ordercenter.injection.component.DaggerShipAddressComponent
import cn.nieking.ordercenter.injection.module.ShipAddressModule
import cn.nieking.ordercenter.presenter.EditShipAddressPresenter
import cn.nieking.ordercenter.presenter.view.EditShipAddressView
import kotlinx.android.synthetic.main.activity_edit_address.*
import org.jetbrains.anko.toast

class ShipAddressEditActivity : BaseMvpActivity<EditShipAddressPresenter>(), EditShipAddressView {

    private var mAddress: ShipAddress? = null

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
        setContentView(R.layout.activity_edit_address)

        initView()
        initData()
    }

    private fun initView() {
        mSaveBtn.onClick {
            if (mShipNameEt.text.isNullOrEmpty() or
                    mShipMobileEt.text.isNullOrEmpty() or
                    mShipAddressEt.text.isNullOrEmpty()) {
                toast("请填写完整的收货信息！")
                return@onClick
            }
            if (mAddress == null) {
                mPresenter.addShipAddress(
                        mShipNameEt.text.toString(),
                        mShipMobileEt.text.toString(),
                        mShipAddressEt.text.toString())
            } else {
                mAddress!!.shipUserName = mShipNameEt.text.toString()
                mAddress!!.shipUserMobile = mShipMobileEt.text.toString()
                mAddress!!.shipAddress = mShipAddressEt.text.toString()
                mPresenter.editShipAddress(mAddress!!)
            }
        }
    }

    private fun initData() {
        mAddress = intent.getParcelableExtra(OrderConstant.KEY_SHIP_ADDRESS)
        mAddress?.let {
            mShipNameEt.setText(it.shipUserName)
            mShipMobileEt.setText(it.shipUserMobile)
            mShipAddressEt.setText(it.shipAddress)
        }
    }

    override fun onAddShipAddressResult(result: Boolean) {
        toast("添加成功！")
        finish()
    }

    override fun onEditShipAddressResult(result: Boolean) {
        toast("修改成功！")
        finish()
    }
}