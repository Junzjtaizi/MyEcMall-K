package cn.nieking.messagecenter.service

import cn.nieking.messagecenter.data.protocol.Message
import rx.Observable

interface MessageService {

    fun getMessageList(): Observable<MutableList<Message>?>
}
