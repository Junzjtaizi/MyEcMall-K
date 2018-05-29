package cn.nieking.goodscenter.service

import cn.nieking.goodscenter.data.protocol.Category
import rx.Observable

interface CategoryService {
    fun getCategory(parentId: Int): Observable<MutableList<Category>?>
}