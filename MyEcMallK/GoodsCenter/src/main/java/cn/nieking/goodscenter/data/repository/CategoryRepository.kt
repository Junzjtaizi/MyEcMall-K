package cn.nieking.goodscenter.data.repository

import cn.nieking.baselibrary.data.net.RetrofitFactory
import cn.nieking.baselibrary.data.protocol.BaseResp
import cn.nieking.goodscenter.data.api.CategoryApi
import cn.nieking.goodscenter.data.protocol.Category
import cn.nieking.goodscenter.data.protocol.GetCategoryReq
import rx.Observable
import javax.inject.Inject

class CategoryRepository @Inject constructor() {

    fun getCategory(parentId: Int): Observable<BaseResp<MutableList<Category>?>> {
        return RetrofitFactory.instance.create(CategoryApi::class.java)
                .getCategory(GetCategoryReq(parentId))
    }
}