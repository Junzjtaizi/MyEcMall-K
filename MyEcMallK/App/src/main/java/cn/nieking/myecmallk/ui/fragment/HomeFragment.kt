package cn.nieking.myecmallk.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.nieking.baselibrary.ui.fragment.BaseFragment
import cn.nieking.baselibrary.widgets.BannerImageLoader
import cn.nieking.myecmallk.R
import cn.nieking.myecmallk.common.HOME_BANNER_FOUR
import cn.nieking.myecmallk.common.HOME_BANNER_ONE
import cn.nieking.myecmallk.common.HOME_BANNER_THREE
import cn.nieking.myecmallk.common.HOME_BANNER_TWO
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater?.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBanner()
        initNews()
    }

    private fun initBanner() {
        mHomeBanner.setImageLoader(BannerImageLoader())
        mHomeBanner.setImages(listOf(HOME_BANNER_ONE, HOME_BANNER_TWO, HOME_BANNER_THREE, HOME_BANNER_FOUR))
        mHomeBanner.setBannerAnimation(Transformer.Accordion)
        mHomeBanner.setDelayTime(2000)
        mHomeBanner.setIndicatorGravity(BannerConfig.RIGHT)
        mHomeBanner.start()
    }

    private fun initNews() {
        mNewsFlipperView.setData(arrayOf("夏日炎炎，第一波福利还有30秒到达战场", "新用户立领100源优惠券"))
    }
}