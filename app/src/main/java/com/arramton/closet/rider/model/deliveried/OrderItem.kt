package com.arramton.closet.rider.model.deliveried

data class OrderItem(
    val address: Address,
    val costume_id: Int,
    val order_reference_no: String,
    val created_at: String,
    val id: Int,
    val order_id: Int,
    val qty: String,
    val updated_at: String,
    val user_id: Int
)