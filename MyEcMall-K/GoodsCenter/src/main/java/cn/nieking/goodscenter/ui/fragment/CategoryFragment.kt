package cn.nieking.goodscenter.ui.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.nieking.baselibrary.ext.setVisible
import cn.nieking.baselibrary.ext.startLoading
import cn.nieking.baselibrary.ui.fragment.BaseMvpFragment
import cn.nieking.goodscenter.R
import cn.nieking.goodscenter.common.GoodsConstant
import cn.nieking.goodscenter.data.protocol.Category
import cn.nieking.goodscenter.injection.component.DaggerCategoryComponent
import cn.nieking.goodscenter.injection.module.CategoryModule
import cn.nieking.goodscenter.presenter.CategoryPresenter
import cn.nieking.goodscenter.presenter.view.CategoryView
import cn.nieking.goodscenter.ui.activity.GoodsActivity
import cn.nieking.goodscenter.ui.adapter.SecondCategoryAdapter
import cn.nieking.goodscenter.ui.adapter.TopCategoryAdapter
import com.kennyc.view.MultiStateView
import kotlinx.android.synthetic.main.fragment_category.*
import org.jetbrains.anko.startActivity

class CategoryFragment : BaseMvpFragment<CategoryPresenter>(), CategoryView {

    private lateinit var topAdapter: TopCategoryAdapter
    private lateinit var secondAdapter: SecondCategoryAdapter

    override fun injectComponent() {
        DaggerCategoryComponent.builder()
                .activityComponent(mActivityComponent)
                .categoryModule(CategoryModule())
                .build()
                .inject(this)
        mPresenter.mView = this
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater?.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        loadData()
    }

    private fun initView() {
        mTopCategoryRv.layoutManager = LinearLayoutManager(activity)
        topAdapter = TopCategoryAdapter(activity)
        mTopCategoryRv.adapter = topAdapter
        topAdapter.onItemClick { item, _ ->
            for (category in topAdapter.dataList) {
                category.isSelected = item.id == category.id
            }
            topAdapter.notifyDataSetChanged()
            loadData(item.id)
        }

        mSecondCategoryRv.layoutManager = GridLayoutManager(activity, 3)
        secondAdapter = SecondCategoryAdapter(activity)
        mSecondCategoryRv.adapter = secondAdapter
        secondAdapter.onItemClick { item, _ ->
            startActivity<GoodsActivity>(GoodsConstant.KEY_CATEGORY_ID to item.id) }
    }

    private fun loadData(parentId: Int = 0) {
        if (parentId != 0) {
            mMultiStateView.startLoading()
        }
        mPresenter.getCategory(parentId)
    }

    override fun onGetCategoryResult(result: MutableList<Category>?) {
        if (result != null && result.size > 0) {
            if (result[0].parentId == 0) {
                result[0].isSelected = true
                topAdapter.setData(result)
                mPresenter.getCategory(result[0].id)
            } else {
                secondAdapter.setData(result)
                mTopCategoryIv.setVisible(true)
                mCategoryTitleTv.setVisible(true)
                mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
            }
        } else {
            mTopCategoryIv.setVisible(false)
            mCategoryTitleTv.setVisible(false)
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
    }
}