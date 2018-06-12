package cn.nieking.messagecenter.data.repository


import cn.nieking.baselibrary.data.net.RetrofitFactory
import cn.nieking.baselibrary.data.protocol.BaseResp
import cn.nieking.messagecenter.data.api.MessageApi
import cn.nieking.messagecenter.data.protocol.Message
import rx.Observable
import javax.inject.Inject


/*
   消息数据层
 */
class MessageRepository @Inject constructor() {

    /*
        获取消息列表
     */
    fun getMessageList(): Observable<BaseResp<MutableList<Message>?>> {
        return RetrofitFactory.instance.create(MessageApi::class.java).getMessageList()
    }


}
