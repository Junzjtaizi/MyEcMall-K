package cn.nieking.goodscenter.ui.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.nieking.baselibrary.ui.adapter.BaseRecyclerViewAdapter
import cn.nieking.baselibrary.ui.fragment.BaseMvpFragment
import cn.nieking.goodscenter.R
import cn.nieking.goodscenter.data.protocol.Category
import cn.nieking.goodscenter.injection.component.DaggerCategoryComponent
import cn.nieking.goodscenter.injection.module.CategoryModule
import cn.nieking.goodscenter.presenter.CategoryPresenter
import cn.nieking.goodscenter.presenter.view.CategoryView
import cn.nieking.goodscenter.ui.adapter.SecondCategoryAdapter
import cn.nieking.goodscenter.ui.adapter.TopCategoryAdapter
import kotlinx.android.synthetic.main.fragment_category.*

class CategoryFragment : BaseMvpFragment<CategoryPresenter>(), CategoryView {

    private lateinit var topAdapter: TopCategoryAdapter
    private lateinit var secondAdapter: SecondCategoryAdapter

    override fun injectComponent() {
        DaggerCategoryComponent.builder()
                .activityComponent(activityComponent)
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
        topAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<Category> {
            override fun onItemClick(item: Category, position: Int) {
                for (category in topAdapter.dataList) {
                    category.isSelected = item.id == category.id
                }
                topAdapter.notifyDataSetChanged()
            }
        })

        mSecondCategoryRv.layoutManager = GridLayoutManager(activity, 3)
        secondAdapter = SecondCategoryAdapter(activity)
        secondAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<Category> {
            override fun onItemClick(item: Category, position: Int) {
                TODO()
            }
        })
    }

    private fun loadData() {
        mPresenter.getCategory(0)
    }

    override fun onGetCategoryResult(result: MutableList<Category>?) {
        result?.let {
            if (it[0].parentId == 0) {
                topAdapter.setData(it)
                mPresenter.getCategory(it[0].id)
            } else {
                secondAdapter.setData(it)
            }
        }
    }
}