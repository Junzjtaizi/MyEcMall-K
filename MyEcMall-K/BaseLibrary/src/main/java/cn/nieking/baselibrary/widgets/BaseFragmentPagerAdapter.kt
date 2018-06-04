package cn.nieking.baselibrary.widgets

import android.annotation.TargetApi
import android.app.Fragment
import android.app.FragmentManager
import android.app.FragmentTransaction
import android.os.Build
import android.os.Parcelable
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup

abstract class BaseFragmentPagerAdapter(fm: FragmentManager) : PagerAdapter() {

    private val mFragmentManager: FragmentManager = fm
    private var mCurTransaction: FragmentTransaction? = null
    private var mCurrentPrimaryItem: Fragment? = null

    abstract fun getItem(position: Int): Fragment

    abstract override fun getCount(): Int

    override fun startUpdate(container: ViewGroup) {
        if (container.id == -1) {
            throw IllegalStateException("ViewPager with adapter " + this + " requires a view id")
        }
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction()
        }

        val itemId = this.getItemId(position)
        val name = makeFragmentName(container.id, itemId)
        var fragment: Fragment? = this.mFragmentManager.findFragmentByTag(name)
        if (fragment != null) {
            this.mCurTransaction!!.attach(fragment)
        } else {
            fragment = this.getItem(position)
            this.mCurTransaction!!.add(container.id, fragment, makeFragmentName(container.id, itemId))
        }

        if (fragment !== this.mCurrentPrimaryItem) {
            fragment.setMenuVisibility(false)
            fragment.userVisibleHint = false
        }

        return fragment
    }

    override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction()
        }

        this.mCurTransaction!!.detach(any as Fragment)
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, any: Any) {
        val fragment = any as Fragment
        if (fragment !== this.mCurrentPrimaryItem) {
            if (this.mCurrentPrimaryItem != null) {
                this.mCurrentPrimaryItem!!.setMenuVisibility(false)
                this.mCurrentPrimaryItem!!.userVisibleHint = false
            }

            if (fragment != null) {
                fragment.setMenuVisibility(true)
                fragment.userVisibleHint = true
            }

            this.mCurrentPrimaryItem = fragment
        }

    }

    @TargetApi(Build.VERSION_CODES.N)
    override fun finishUpdate(container: ViewGroup) {
        if (this.mCurTransaction != null) {
            this.mCurTransaction!!.commitNowAllowingStateLoss()
            this.mCurTransaction = null
        }

    }

    override fun isViewFromObject(view: View, any: Any): Boolean {
        return (any as Fragment).view == view
    }

    override fun saveState(): Parcelable? {
        return null
    }

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {}

    open fun getItemId(position: Int): Long {
        return position.toLong()
    }

    private fun makeFragmentName(viewId: Int, id: Long): String {
        return "android:switcher:$viewId:$id"
    }
}