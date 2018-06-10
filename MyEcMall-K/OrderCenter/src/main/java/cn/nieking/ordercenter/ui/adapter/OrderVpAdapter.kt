package cn.nieking.ordercenter.ui.adapter

import android.app.Fragment
import android.app.FragmentManager
import android.content.Context
import android.os.Bundle
import cn.nieking.baselibrary.widgets.BaseFragmentPagerAdapter
import cn.nieking.ordercenter.common.OrderConstant
import cn.nieking.ordercenter.ui.fragment.OrderFragment

/*
    订单Tab对应ViewPager
 */
class OrderVpAdapter(fm: FragmentManager, context: Context) : BaseFragmentPagerAdapter(fm) {

    private val titles = arrayOf("全部", "待付款", "待收货", "已完成", "已取消")

    override fun getItem(position: Int): Fragment {
        val fragment = OrderFragment()
        val bundle = Bundle()
        bundle.putInt(OrderConstant.KEY_ORDER_STATUS, position)
        fragment.arguments = bundle
        return fragment
    }

    override fun getCount(): Int {
        return titles.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titles[position]
    }
}
