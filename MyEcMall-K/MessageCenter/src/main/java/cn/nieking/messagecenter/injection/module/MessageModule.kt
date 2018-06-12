package cn.nieking.messagecenter.injection.module

import cn.nieking.messagecenter.service.MessageService
import cn.nieking.messagecenter.service.impl.MessageServiceImpl
import dagger.Module
import dagger.Provides

@Module
class MessageModule {

    @Provides
    fun provideMessageService(service: MessageServiceImpl): MessageService {
        return service
    }
}
