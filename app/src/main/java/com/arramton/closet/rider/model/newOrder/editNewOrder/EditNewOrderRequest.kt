package com.arramton.closet.rider.model.newOrder.editNewOrder

data class EditNewOrderRequest(
    val items: List<Item>,
    val order_id: Int
)