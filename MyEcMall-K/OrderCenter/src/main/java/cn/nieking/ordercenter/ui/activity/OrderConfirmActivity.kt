package cn.nieking.ordercenter.ui.activity

import android.os.Bundle
import cn.nieking.baselibrary.ui.activity.BaseActivity
import cn.nieking.ordercenter.R
import cn.nieking.provider.router.RouterPath
import com.alibaba.android.arouter.facade.annotation.Route

@Route(path = RouterPath.OrderCenter.PATH_ORDER_CONFIRM)
class OrderConfirmActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_confirm)
    }
}