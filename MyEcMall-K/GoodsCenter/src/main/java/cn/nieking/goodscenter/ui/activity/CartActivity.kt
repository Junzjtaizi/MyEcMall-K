package cn.nieking.goodscenter.ui.activity

import android.os.Bundle
import cn.nieking.baselibrary.ui.activity.BaseActivity
import cn.nieking.goodscenter.R
import cn.nieking.goodscenter.ui.fragment.CartFragment

class CartActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val fragment = fragmentManager.findFragmentById(R.id.fragment_cart)
        (fragment as CartFragment).setBackVisible(true)
    }
}