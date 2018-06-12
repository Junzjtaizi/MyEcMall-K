package cn.nieking.messagecenter.presenter

import cn.nieking.baselibrary.ext.execute
import cn.nieking.baselibrary.presenter.BasePresenter
import cn.nieking.baselibrary.rx.BaseSubscriber
import cn.nieking.messagecenter.data.protocol.Message
import cn.nieking.messagecenter.presenter.view.MessageView
import cn.nieking.messagecenter.service.MessageService
import javax.inject.Inject

class MessagePresenter @Inject constructor() : BasePresenter<MessageView>() {

    @Inject
    lateinit var messageService: MessageService

    fun getMessageList() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        messageService.getMessageList().execute(object : BaseSubscriber<MutableList<Message>?>(mView) {
            override fun onNext(t: MutableList<Message>?) {
                mView.onGetMessageListResult(t)
            }
        }, lifecycleProvider)
    }
}