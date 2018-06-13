package cn.nieking.myecmallk.ui.activity

import android.app.Fragment
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cn.nieking.baselibrary.common.AppManager
import cn.nieking.baselibrary.utils.AppPrefsUtils
import cn.nieking.goodscenter.common.GoodsConstant
import cn.nieking.goodscenter.event.UpdateCartSizeEvent
import cn.nieking.goodscenter.ui.fragment.CartFragment
import cn.nieking.goodscenter.ui.fragment.CategoryFragment
import cn.nieking.messagecenter.ui.fragment.MessageFragment
import cn.nieking.myecmallk.R
import cn.nieking.myecmallk.ui.fragment.HomeFragment
import cn.nieking.myecmallk.ui.fragment.MeFragment
import cn.nieking.provider.event.MessageBadgeEvent
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import java.util.*

class MainActivity : AppCompatActivity() {

    private val mStack = Stack<Fragment>()
    private val mHomeFragment by lazy { HomeFragment() }
    private val mCategoryFragment by lazy { CategoryFragment() }
    private val mCartFragment by lazy { CartFragment() }
    private val mMsgFragment by lazy { MessageFragment() }
    private val mMeFragment by lazy { MeFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFragment()
        initBottomNav()
        initObserve()
        changeFragment(0)
        loadCartSize()
    }

    private fun initFragment() {
        val manager = fragmentManager.beginTransaction()
        manager.add(R.id.mContainer, mHomeFragment)
        manager.add(R.id.mContainer, mCategoryFragment)
        manager.add(R.id.mContainer, mCartFragment)
        manager.add(R.id.mContainer, mMsgFragment)
        manager.add(R.id.mContainer, mMeFragment)
        manager.commit()

        mStack.add(mHomeFragment)
        mStack.add(mCategoryFragment)
        mStack.add(mCartFragment)
        mStack.add(mMsgFragment)
        mStack.add(mMeFragment)
    }

    private fun initBottomNav() {
        mBottomNavBar.setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener {
            override fun onTabSelected(position: Int) {
                changeFragment(position)
            }
            override fun onTabReselected(position: Int) {}
            override fun onTabUnselected(position: Int) {}
        })
        mBottomNavBar.checkMsgBadge(false)
    }

    private fun initObserve() {
        Bus.observe<UpdateCartSizeEvent>()
                .subscribe {
                    loadCartSize()
                }.registerInBus(this)
        Bus.observe<MessageBadgeEvent>()
                .subscribe {
                    mBottomNavBar.checkMsgBadge(it.isVisible)
                }.registerInBus(this)
    }

    private fun loadCartSize() {
        mBottomNavBar.checkCartBadge(AppPrefsUtils.getInt(GoodsConstant.SP_CART_SIZE))
    }

    private fun changeFragment(position: Int) {
        val manager = fragmentManager.beginTransaction()
        for (fragment in mStack) {
            manager.hide(fragment)
        }
        manager.show(mStack[position])
        manager.commit()
    }

    override fun onDestroy() {
        Bus.unregister(this)
        super.onDestroy()
    }

    private var pressTime: Long = 0

    override fun onBackPressed() {
        val time = System.currentTimeMillis()
        if (time - pressTime > 2000) {
            toast("再按一次退出")
            pressTime = time
        } else {
            AppManager.instance.exitApp(this)
        }
    }
}
