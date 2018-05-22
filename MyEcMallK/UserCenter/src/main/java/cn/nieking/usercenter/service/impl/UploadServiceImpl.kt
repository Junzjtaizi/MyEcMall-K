package cn.nieking.usercenter.service.impl

import cn.nieking.baselibrary.ext.convert
import cn.nieking.usercenter.data.repository.UploadRepository
import cn.nieking.usercenter.service.UploadService
import rx.Observable
import javax.inject.Inject

class UploadServiceImpl @Inject constructor() : UploadService {

    @Inject
    lateinit var repository: UploadRepository

    override fun getUploadToken(): Observable<String> {
        return repository
                .getUploadToken()
                .convert()
    }
}