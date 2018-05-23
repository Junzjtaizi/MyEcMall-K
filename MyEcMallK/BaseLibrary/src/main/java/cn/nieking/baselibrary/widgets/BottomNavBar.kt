package cn.nieking.baselibrary.widgets

import android.content.Context
import android.util.AttributeSet
import cn.nieking.baselibrary.R
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.ashokvarma.bottomnavigation.ShapeBadgeItem
import com.ashokvarma.bottomnavigation.TextBadgeItem

class BottomNavBar @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : BottomNavigationBar(context, attrs, defStyleAttr) {

    private val mCartBadge: TextBadgeItem = TextBadgeItem()
    private val mMsgBadge: ShapeBadgeItem = ShapeBadgeItem().setShape(ShapeBadgeItem.SHAPE_OVAL)

    init {
        val homeItem = BottomNavigationItem(R.drawable.btn_nav_home_press, resources.getString(R.string.nav_bar_home))
                .setInactiveIconResource(R.drawable.btn_nav_home_normal)
                .setActiveColorResource(R.color.common_blue)
                .setInActiveColorResource(R.color.text_normal)
        val categoryItem = BottomNavigationItem(R.drawable.btn_nav_category_press, resources.getString(R.string.nav_bar_category))
                .setInactiveIconResource(R.drawable.btn_nav_category_normal)
                .setActiveColorResource(R.color.common_blue)
                .setInActiveColorResource(R.color.text_normal)
        val cartItem = BottomNavigationItem(R.drawable.btn_nav_cart_press, resources.getString(R.string.nav_bar_cart))
                .setInactiveIconResource(R.drawable.btn_nav_cart_normal)
                .setActiveColorResource(R.color.common_blue)
                .setInActiveColorResource(R.color.text_normal)
                .setBadgeItem(mCartBadge)
        val msgItem = BottomNavigationItem(R.drawable.btn_nav_msg_press, resources.getString(R.string.nav_bar_msg))
                .setInactiveIconResource(R.drawable.btn_nav_msg_normal)
                .setActiveColorResource(R.color.common_blue)
                .setInActiveColorResource(R.color.text_normal)
                .setBadgeItem(mMsgBadge)
        val userItem = BottomNavigationItem(R.drawable.btn_nav_user_press, resources.getString(R.string.nav_bar_user))
                .setInactiveIconResource(R.drawable.btn_nav_user_normal)
                .setActiveColorResource(R.color.common_blue)
                .setInActiveColorResource(R.color.text_normal)

        setMode(BottomNavigationBar.MODE_FIXED)
        setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
        setBarBackgroundColor(R.color.common_white)

        addItem(homeItem)
        addItem(categoryItem)
        addItem(cartItem)
        addItem(msgItem)
        addItem(userItem)
        setFirstSelectedPosition(0)
        initialise()
    }

    fun checkCartBadge(count: Int) {
        if (count == 0) {
            mCartBadge.hide()
        } else {
            mCartBadge.show()
            mCartBadge.setText("$count")
        }
    }

    fun checkMsgBadge(isShow: Boolean) {
        if (isShow) {
            mMsgBadge.show()
        } else {
            mMsgBadge.hide()
        }
    }
}