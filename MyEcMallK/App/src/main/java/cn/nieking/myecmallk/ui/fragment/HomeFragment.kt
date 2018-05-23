package cn.nieking.myecmallk.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.nieking.baselibrary.ui.fragment.BaseFragment
import cn.nieking.myecmallk.R

class HomeFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater?.inflate(R.layout.fragment_home, null)
        initView()
        return rootView
    }

    private fun initView() {

    }
}