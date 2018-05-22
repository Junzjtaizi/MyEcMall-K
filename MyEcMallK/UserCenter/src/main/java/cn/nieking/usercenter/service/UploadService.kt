package cn.nieking.usercenter.service

import rx.Observable

interface UploadService {
    fun getUploadToken(): Observable<String>
}