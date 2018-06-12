package cn.nieking.messagecenter.presenter.view

import cn.nieking.baselibrary.presenter.view.BaseView
import cn.nieking.messagecenter.data.protocol.Message

interface MessageView : BaseView {

    fun onGetMessageListResult(result: MutableList<Message>?)
}
