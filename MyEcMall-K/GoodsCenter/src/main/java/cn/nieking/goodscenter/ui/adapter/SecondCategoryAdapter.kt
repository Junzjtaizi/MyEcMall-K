package cn.nieking.goodscenter.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.nieking.baselibrary.ext.loadUrl
import cn.nieking.baselibrary.ui.adapter.BaseRecyclerViewAdapter
import cn.nieking.goodscenter.R
import cn.nieking.goodscenter.data.protocol.Category
import kotlinx.android.synthetic.main.layout_second_category_item.view.*

class SecondCategoryAdapter(context: Context)
    : BaseRecyclerViewAdapter<Category, SecondCategoryAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_second_category_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.itemView.mCategoryIconIv.loadUrl(dataList[position].categoryIcon)
        holder.itemView.mSecondCategoryNameTv.text = dataList[position].categoryName
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}