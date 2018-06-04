package cn.nieking.goodscenter.ui.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import cn.nieking.baselibrary.ui.activity.BaseActivity
import cn.nieking.goodscenter.R
import cn.nieking.goodscenter.ui.adapter.GoodsDetailVpAdapter
import kotlinx.android.synthetic.main.activity_goods_detail.*

class GoodsDetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods_detail)

        initView()
    }

    private fun initView() {
        mGoodsDetailTab.tabMode = TabLayout.MODE_FIXED
        mGoodsDetailVp.adapter = GoodsDetailVpAdapter(fragmentManager, this)
        mGoodsDetailTab.setupWithViewPager(mGoodsDetailVp)
    }
}