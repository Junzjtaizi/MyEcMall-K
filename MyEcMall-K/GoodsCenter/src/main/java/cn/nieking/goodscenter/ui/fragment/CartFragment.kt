package cn.nieking.goodscenter.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.nieking.baselibrary.ext.startLoading
import cn.nieking.baselibrary.ui.fragment.BaseMvpFragment
import cn.nieking.goodscenter.R
import cn.nieking.goodscenter.data.protocol.CartGoods
import cn.nieking.goodscenter.injection.component.DaggerCartComponent
import cn.nieking.goodscenter.injection.module.CartModule
import cn.nieking.goodscenter.presenter.CartListPresenter
import cn.nieking.goodscenter.presenter.CategoryPresenter
import cn.nieking.goodscenter.presenter.view.CartListView
import cn.nieking.goodscenter.presenter.view.CategoryView
import cn.nieking.goodscenter.ui.adapter.CartGoodsAdapter
import com.kennyc.view.MultiStateView
import kotlinx.android.synthetic.main.fragment_cart.*

class CartFragment : BaseMvpFragment<CartListPresenter>(), CartListView {

    private lateinit var mAdapter: CartGoodsAdapter

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
        loadData()
    }

    private fun initView() {
        mCartGoodsRv.layoutManager = LinearLayoutManager(activity)
        mAdapter = CartGoodsAdapter(activity)
        mCartGoodsRv.adapter = mAdapter
    }

    private fun loadData() {
        mMultiStateView.startLoading()
        mPresenter.getCartList()
    }

    override fun onGetCartListResult(result: MutableList<CartGoods>?) {
        if (result != null && result.size > 0) {
            mAdapter.setData(result)
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        } else {
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
    }

    override fun onDeleteCartListResult(result: Boolean) {
    }

    override fun onSubmitCartListResult(result: Int) {
    }
}