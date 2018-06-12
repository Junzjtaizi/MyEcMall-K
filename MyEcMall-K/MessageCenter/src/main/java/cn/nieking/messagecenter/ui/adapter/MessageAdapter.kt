package cn.nieking.messagecenter.ui.adapter


import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.nieking.baselibrary.ext.loadUrl
import cn.nieking.baselibrary.ui.adapter.BaseRecyclerViewAdapter
import cn.nieking.messagecenter.R
import cn.nieking.messagecenter.data.protocol.Message
import kotlinx.android.synthetic.main.layout_message_item.view.*

/*
    消息数据
 */
class MessageAdapter(context: Context) : BaseRecyclerViewAdapter<Message, MessageAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext)
                .inflate(R.layout.layout_message_item,
                        parent,
                        false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]
        //消息图标
        holder.itemView.mMsgIconIv.loadUrl(model.msgIcon)
        //消息标题
        holder.itemView.mMsgTitleTv.text = model.msgTitle
        //消息内容
        holder.itemView.mMsgContentTv.text = model.msgContent
        //消息时间
        holder.itemView.mMsgTimeTv.text = model.msgTime
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
