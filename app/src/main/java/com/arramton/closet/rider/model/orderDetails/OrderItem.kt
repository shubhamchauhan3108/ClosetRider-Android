package com.arramton.closet.rider.model.orderDetails

import com.arramton.closet.rider.model.orderDetails.CostumesOrderItem

data class OrderItem(
    val active: Int,
    val costumes_order_item: List<CostumesOrderItem>,
    val created_at: String,
    val id: Int,
    val name: String,
    val updated_at: String
)