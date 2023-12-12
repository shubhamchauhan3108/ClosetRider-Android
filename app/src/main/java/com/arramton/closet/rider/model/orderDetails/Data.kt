package com.arramton.closet.rider.model.orderDetails

data class Data(
    val order: Order,
    val orderItem: List<OrderItem>,
    val status: List<Statu>
)