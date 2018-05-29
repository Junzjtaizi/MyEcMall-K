package cn.nieking.goodscenter.data.api

import cn.nieking.baselibrary.data.protocol.BaseResp
import cn.nieking.goodscenter.data.protocol.Category
import cn.nieking.goodscenter.data.protocol.GetCategoryReq
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

interface CategoryApi {

    @POST("category/getCategory")
    fun getCategory(@Body req: GetCategoryReq): Observable<BaseResp<MutableList<Category>?>>
}