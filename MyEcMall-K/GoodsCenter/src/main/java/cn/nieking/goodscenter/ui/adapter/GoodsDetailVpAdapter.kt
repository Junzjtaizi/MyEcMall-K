package cn.nieking.goodscenter.ui.adapter

import android.app.Fragment
import android.app.FragmentManager
import android.content.Context
import cn.nieking.baselibrary.widgets.BaseFragmentPagerAdapter
import cn.nieking.goodscenter.ui.fragment.GoodsDetailTabOneFragment
import cn.nieking.goodscenter.ui.fragment.GoodsDetailTabTwoFragment

class GoodsDetailVpAdapter(fm: FragmentManager, context: Context) : BaseFragmentPagerAdapter(fm) {

    private val title = arrayOf("商品", "详情")

    override fun getItem(position: Int): Fragment {
        return if (position == 0) {
            GoodsDetailTabOneFragment()
        } else {
            GoodsDetailTabTwoFragment()
        }
    }

    override fun getCount(): Int {
        return title.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return title[position]
    }
}