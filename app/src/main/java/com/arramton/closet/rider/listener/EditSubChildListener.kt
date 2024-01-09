package com.arramton.closet.rider.listener

import com.arramton.closet.rider.model.orderDetails.OrderItemX

interface EditSubChildListener  {

    fun onClickOpenCamera(imgeId:Int)
    fun onClick(remark:String,costume_id:Int,order_item_id:Int,image:String)
//    fun onClick( list: ArrayList<OrderItemX>)
}