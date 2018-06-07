package cn.nieking.goodscenter.ui.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.view.Gravity
import cn.nieking.baselibrary.ext.onClick
import cn.nieking.baselibrary.ui.activity.BaseActivity
import cn.nieking.baselibrary.utils.AppPrefsUtils
import cn.nieking.goodscenter.R
import cn.nieking.goodscenter.common.GoodsConstant
import cn.nieking.goodscenter.event.AddCartEvent
import cn.nieking.goodscenter.event.UpdateCartSizeEvent
import cn.nieking.goodscenter.ui.adapter.GoodsDetailVpAdapter
import cn.nieking.provider.common.afterLogin
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import kotlinx.android.synthetic.main.activity_goods_detail.*
import org.jetbrains.anko.startActivity
import q.rorbin.badgeview.QBadgeView

class GoodsDetailActivity : BaseActivity() {

    private lateinit var mCartBadge: QBadgeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods_detail)

        initView()
        initObserve()
        loadCartSize()
    }

    private fun initView() {
        mGoodsDetailTab.tabMode = TabLayout.MODE_FIXED
        mGoodsDetailVp.adapter = GoodsDetailVpAdapter(fragmentManager, this)
        mGoodsDetailTab.setupWithViewPager(mGoodsDetailVp)

        mAddCartBtn.onClick {
            afterLogin { Bus.send(AddCartEvent()) }
        }

        mEnterCartTv.onClick {
            startActivity<CartActivity>()
        }

        mLeftIv.onClick {
            finish()
        }

        mCartBadge = QBadgeView(this)
    }

    private fun initObserve() {
        Bus.observe<UpdateCartSizeEvent>()
                .subscribe {
                    setCartBadge()
                }.registerInBus(this)
    }

    private fun loadCartSize() {
        setCartBadge()
    }

    private fun setCartBadge() {
        mCartBadge.badgeGravity = Gravity.END or Gravity.TOP
        mCartBadge.setGravityOffset(22f, -2f, true)
        mCartBadge.setBadgeTextSize(6f, true)
        mCartBadge.bindTarget(mEnterCartTv).badgeNumber = AppPrefsUtils.getInt(GoodsConstant.SP_CART_SIZE)
    }

    override fun onDestroy() {
        Bus.unregister(this)
        super.onDestroy()
    }
}