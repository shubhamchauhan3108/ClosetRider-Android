package com.arramton.closet.rider.model.orderDetails

data class CostumesOrderItem(
    val category_id: Int,
    val created_at: String,
    val dry_clean_charge: Int,
    val id: Int,
    var image_url: String,
    val item_price: String,
    val name: String,
    val order_item: List<OrderItemX>,
    val updated_at: String
)