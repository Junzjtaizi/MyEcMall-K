package cn.nieking.goodscenter.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.nieking.baselibrary.ext.onClick
import cn.nieking.baselibrary.ext.setVisible
import cn.nieking.baselibrary.ext.startLoading
import cn.nieking.baselibrary.ui.fragment.BaseMvpFragment
import cn.nieking.baselibrary.utils.AppPrefsUtils
import cn.nieking.baselibrary.utils.YuanFenConverter
import cn.nieking.goodscenter.R
import cn.nieking.goodscenter.common.GoodsConstant
import cn.nieking.goodscenter.data.protocol.CartGoods
import cn.nieking.goodscenter.event.CartAllCheckedEvent
import cn.nieking.goodscenter.event.UpdateCartSizeEvent
import cn.nieking.goodscenter.event.UpdateTotalPriceEvent
import cn.nieking.goodscenter.injection.component.DaggerCartComponent
import cn.nieking.goodscenter.injection.module.CartModule
import cn.nieking.goodscenter.presenter.CartListPresenter
import cn.nieking.goodscenter.presenter.view.CartListView
import cn.nieking.goodscenter.ui.adapter.CartGoodsAdapter
import cn.nieking.provider.common.ProviderConstant
import cn.nieking.provider.router.RouterPath
import com.alibaba.android.arouter.launcher.ARouter
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.kennyc.view.MultiStateView
import kotlinx.android.synthetic.main.fragment_cart.*
import org.jetbrains.anko.toast

class CartFragment : BaseMvpFragment<CartListPresenter>(), CartListView {

    private lateinit var mAdapter: CartGoodsAdapter
    private var mTotalPrice: Long = 0

    override fun injectComponent() {
        DaggerCartComponent.builder()
                .activityComponent(mActivityComponent)
                .cartModule(CartModule())
                .build()
                .inject(this)
        mPresenter.mView = this
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater?.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserve()
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun initView() {
        mCartGoodsRv.layoutManager = LinearLayoutManager(activity)
        mAdapter = CartGoodsAdapter(activity)
        mCartGoodsRv.adapter = mAdapter

        mHeaderBar.getRightView().onClick {
            refreshEditStatus()
        }

        mAllCheckedCb.onClick {
            for (item in mAdapter.dataList) {
                item.isSelected = mAllCheckedCb.isChecked
            }
            mAdapter.notifyDataSetChanged()
        }

        mDeleteBtn.onClick {
            val cartIdList: MutableList<Int> = arrayListOf()
            mAdapter.dataList
                    .filter { it.isSelected }
                    .mapTo(cartIdList) { it.id }
            if (cartIdList.size == 0) {
                toast("请选择需要删除的商品")
            } else {
                mPresenter.deleteCartList(cartIdList)
            }
        }

        mSettleAccountsBtn.onClick {
            val cartGoodsList: MutableList<CartGoods> = arrayListOf()
            mAdapter.dataList
                    .filter { it.isSelected }
                    .mapTo(cartGoodsList) { it }
            if (cartGoodsList.size == 0) {
                toast("请选择需要购买的商品")
            } else {
                mPresenter.submitCart(cartGoodsList, mTotalPrice)
            }
        }
    }

    private fun refreshEditStatus() {
        val isEditStatus = getString(R.string.common_edit) == mHeaderBar.getRightText()
        mTotalPriceTv.setVisible(isEditStatus.not())
        mSettleAccountsBtn.setVisible(isEditStatus.not())
        mDeleteBtn.setVisible(isEditStatus)
        mHeaderBar.getRightView().text = if (isEditStatus) getString(R.string.common_complete) else getString(R.string.common_edit)
    }

    private fun initObserve() {
        Bus.observe<CartAllCheckedEvent>()
                .subscribe {
                    mAllCheckedCb.isChecked = it.isAllChecked
                    updateTotalPrice()
                }.registerInBus(this)

        Bus.observe<UpdateTotalPriceEvent>()
                .subscribe {
                    updateTotalPrice()
                }.registerInBus(this)
    }

    private fun loadData() {
        mMultiStateView.startLoading()
        mPresenter.getCartList()
    }

    private fun updateTotalPrice() {
        mTotalPrice = mAdapter.dataList
                .filter { it.isSelected }
                .map { it.goodsPrice * it.goodsCount }
                .sum()
        mTotalPriceTv.text = "合计：${YuanFenConverter.changeF2YWithUnit(mTotalPrice)}"
    }

    override fun onGetCartListResult(result: MutableList<CartGoods>?) {
        if (result != null && result.size > 0) {
            mAdapter.setData(result)
            mHeaderBar.getRightView().setVisible(true)
            mAllCheckedCb.isChecked = false
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        } else {
            mHeaderBar.getRightView().setVisible(false)
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
        AppPrefsUtils.putInt(GoodsConstant.SP_CART_SIZE, result?.size?:0)
        Bus.send(UpdateCartSizeEvent())
        updateTotalPrice()
    }

    override fun onDeleteCartListResult(result: Boolean) {
        toast("删除成功")
        refreshEditStatus()
        loadData()
    }

    override fun onSubmitCartListResult(result: Int) {
        ARouter.getInstance().build(RouterPath.OrderCenter.PATH_ORDER_CONFIRM)
                .withInt(ProviderConstant.KEY_ORDER_ID, result)
                .navigation()
    }

    fun setBackVisible(isVisible: Boolean) {
        mHeaderBar.getLeftView().setVisible(isVisible)
    }
}