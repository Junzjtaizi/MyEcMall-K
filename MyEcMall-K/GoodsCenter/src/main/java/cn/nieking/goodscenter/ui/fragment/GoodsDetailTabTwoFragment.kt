package cn.nieking.goodscenter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.nieking.baselibrary.ext.loadUrl
import cn.nieking.baselibrary.ui.fragment.BaseFragment
import cn.nieking.goodscenter.R
import cn.nieking.goodscenter.event.GoodsDetailImageEvent
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import kotlinx.android.synthetic.main.fragment_goods_detail_tab_two.*

class GoodsDetailTabTwoFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater?.inflate(R.layout.fragment_goods_detail_tab_two, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserve()
    }

    private fun initObserve() {
        Bus.observe<GoodsDetailImageEvent>()
                .subscribe {
                    mGoodsDetailOneIv.loadUrl(it.imgOne)
                    mGoodsDetailTwoIv.loadUrl(it.imgTwo)
                }
                .registerInBus(this)
    }

    override fun onDestroy() {
        Bus.unregister(this)
        super.onDestroy()
    }
}