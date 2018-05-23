package cn.nieking.myecmallk.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import cn.nieking.myecmallk.R
import cn.nieking.myecmallk.ui.fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mBottomNavBar.checkMsgBadge(false)
        mBottomNavBar.checkCartBadge(20)

        initView()
    }

    private fun initView() {
        val manager = fragmentManager.beginTransaction()
        manager.replace(R.id.mContainer, HomeFragment())
        manager.commit()
    }
}
