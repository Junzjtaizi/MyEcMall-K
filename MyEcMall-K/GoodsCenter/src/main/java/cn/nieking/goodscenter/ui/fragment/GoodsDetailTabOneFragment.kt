package cn.nieking.goodscenter.ui.fragment

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import cn.nieking.baselibrary.ext.onClick
import cn.nieking.baselibrary.ui.activity.BaseActivity
import cn.nieking.baselibrary.ui.fragment.BaseMvpFragment
import cn.nieking.baselibrary.utils.YuanFenConverter
import cn.nieking.baselibrary.widgets.BannerImageLoader
import cn.nieking.goodscenter.R
import cn.nieking.goodscenter.common.GoodsConstant
import cn.nieking.goodscenter.data.protocol.Goods
import cn.nieking.goodscenter.event.AddCartEvent
import cn.nieking.goodscenter.event.GoodsDetailImageEvent
import cn.nieking.goodscenter.event.SkuChangedEvent
import cn.nieking.goodscenter.event.UpdateCartSizeEvent
import cn.nieking.goodscenter.injection.component.DaggerGoodsComponent
import cn.nieking.goodscenter.injection.module.GoodsModule
import cn.nieking.goodscenter.presenter.GoodsDetailPresenter
import cn.nieking.goodscenter.presenter.view.GoodsDetailView
import cn.nieking.goodscenter.widget.GoodsSkuPopView
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.fragment_goods_detail_tab_one.*
import org.jetbrains.anko.contentView
import org.jetbrains.anko.toast

class GoodsDetailTabOneFragment : BaseMvpFragment<GoodsDetailPresenter>(), GoodsDetailView {

    private lateinit var mSkuPop: GoodsSkuPopView
    private lateinit var mAnimationStart: ScaleAnimation
    private lateinit var mAnimationEnd: ScaleAnimation
    private var mCurGoods: Goods? = null

    override fun injectComponent() {
        DaggerGoodsComponent.builder()
                .activityComponent(mActivityComponent)
                .goodsModule(GoodsModule())
                .build()
                .inject(this)
        mPresenter.mView = this
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater?.inflate(R.layout.fragment_goods_detail_tab_one, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAnim()
        initSkuPop()
        loadData()
        initObserve()
    }

    private fun initView() {
        mGoodsDetailBanner.setImageLoader(BannerImageLoader())
        mGoodsDetailBanner.setBannerAnimation(Transformer.Accordion)
        mGoodsDetailBanner.setDelayTime(2000)
        mGoodsDetailBanner.setIndicatorGravity(BannerConfig.RIGHT)

        mSkuView.onClick {
            mSkuPop.showAtLocation(
                    activity.contentView,
                    Gravity.BOTTOM and Gravity.CENTER_HORIZONTAL,
                    0, 0)
            (activity as BaseActivity).contentView.startAnimation(mAnimationStart)
        }
    }

    private fun initSkuPop() {
        mSkuPop = GoodsSkuPopView(activity)
        mSkuPop.setOnDismissListener {
            (activity as BaseActivity).contentView.startAnimation(mAnimationEnd)
        }
    }

    private fun loadData() {
        val id = activity.intent.getIntExtra(GoodsConstant.KEY_GOODS_ID, -1)
        mPresenter.getGoodsDetail(id)
    }

    private fun loadPopData(result: Goods) {
        mSkuPop.setGoodsIcon(result.goodsDefaultIcon)
        mSkuPop.setGoodsCode(result.goodsCode)
        mSkuPop.setGoodsPrice(result.goodsDefaultPrice)

        mSkuPop.setSkuData(result.goodsSku)
    }

    private fun initObserve() {
       Bus.observe<SkuChangedEvent>()
               .subscribe {
                   mSkuSelectedTv.text = "${mSkuPop.getSelectSku()}${GoodsConstant.SKU_SEPARATOR}${mSkuPop.getSelectCount()}件"
               }.registerInBus(this)

       Bus.observe<AddCartEvent>()
               .subscribe {
                   addCart()
               }.registerInBus(this)
    }

    override fun onDestroy() {
        Bus.unregister(this)
        super.onDestroy()
    }

    private fun addCart() {
        mCurGoods?.let {
            mPresenter.addCart(
                    it.id,
                    it.goodsDesc,
                    it.goodsDefaultIcon,
                    it.goodsDefaultPrice,
                    mSkuPop.getSelectCount(),
                    mSkuPop.getSelectSku())
        }
    }

    private fun initAnim() {
        mAnimationStart = ScaleAnimation(
                1f, 0.95f, 1f, 0.95f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        )
        mAnimationStart.duration = 300
        mAnimationStart.fillAfter = true

        mAnimationEnd = ScaleAnimation(
                0.95f, 1f, 0.95f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        )
        mAnimationEnd.duration = 300
        mAnimationEnd.fillAfter = true
    }

    override fun onGetGoodsDetailResult(result: Goods) {
        mCurGoods = result

        mGoodsDetailBanner.setImages(result.goodsBanner.split(","))
        mGoodsDetailBanner.start()

        mGoodsDescTv.text = result.goodsDesc
        mGoodsPriceTv.text = YuanFenConverter.changeF2YWithUnit(result.goodsDefaultPrice)
        mSkuSelectedTv.text = result.goodsDefaultSku

        Bus.send(GoodsDetailImageEvent(result.goodsDetailOne, result.goodsDetailTwo))

        loadPopData(result)
    }

    override fun onAddCartResult(result: Int) {
        Bus.send(UpdateCartSizeEvent())
    }
}