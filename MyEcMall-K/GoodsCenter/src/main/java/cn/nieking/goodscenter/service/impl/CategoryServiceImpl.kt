package cn.nieking.goodscenter.service.impl

import cn.nieking.baselibrary.ext.convert
import cn.nieking.goodscenter.data.protocol.Category
import cn.nieking.goodscenter.data.repository.CategoryRepository
import cn.nieking.goodscenter.service.CategoryService
import rx.Observable
import javax.inject.Inject

class CategoryServiceImpl @Inject constructor() : CategoryService {

    @Inject
    lateinit var repository: CategoryRepository

    override fun getCategory(parentId: Int): Observable<MutableList<Category>?> {
        return repository.getCategory(parentId).convert()
    }
}