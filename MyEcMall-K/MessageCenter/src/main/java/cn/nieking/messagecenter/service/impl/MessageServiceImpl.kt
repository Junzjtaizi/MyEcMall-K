package cn.nieking.messagecenter.service.impl

import cn.nieking.baselibrary.ext.convert
import cn.nieking.messagecenter.data.protocol.Message
import cn.nieking.messagecenter.data.repository.MessageRepository
import cn.nieking.messagecenter.service.MessageService
import rx.Observable
import javax.inject.Inject

class MessageServiceImpl @Inject constructor() : MessageService {

    @Inject
    lateinit var repository: MessageRepository

    override fun getMessageList(): Observable<MutableList<Message>?> {
        return repository.getMessageList().convert()
    }
}
