package cn.nieking.ordercenter.data.api

import cn.nieking.baselibrary.data.protocol.BaseResp
import cn.nieking.ordercenter.data.protocol.AddShipAddressReq
import cn.nieking.ordercenter.data.protocol.DeleteShipAddressReq
import cn.nieking.ordercenter.data.protocol.EditShipAddressReq
import cn.nieking.ordercenter.data.protocol.ShipAddress
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable


/*
    地址管理 接口
 */
interface ShipAddressApi {

    /*
        添加收货地址
     */
    @POST("shipAddress/add")
    fun addShipAddress(@Body req: AddShipAddressReq): Observable<BaseResp<String>>

    /*
        删除收货地址
     */
    @POST("shipAddress/delete")
    fun deleteShipAddress(@Body req: DeleteShipAddressReq): Observable<BaseResp<String>>

    /*
        修改收货地址
     */
    @POST("shipAddress/modify")
    fun editShipAddress(@Body req: EditShipAddressReq): Observable<BaseResp<String>>

    /*
        查询收货地址列表
     */
    @POST("shipAddress/getList")
    fun getShipAddressList(): Observable<BaseResp<MutableList<ShipAddress>?>>

}
