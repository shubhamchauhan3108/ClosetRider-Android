package com.arramton.closet.rider.model.orderDetails

import com.arramton.closet.rider.model.newOrder.Costume
import com.arramton.closet.rider.model.orderDetails.OrderItemStatus

data class OrderItemX(
    val admin_warning: String,
    val costume_id: Int,
    val costume_reference_no: Int,
    val created_at: String,
    val id: Int,
    val order_id: Int,
    val order_item_status: OrderItemStatus,
    val qty: Int,
    val status: Int,
    val updated_at: String,
    val user_id: Int,
    val costume: Costume,

    )