package com.arramton.closet.rider.model.newOrder

data class OrderItem(
    val admin_warning: Any,
    val costume: Costume,
    val costume_id: Int,
    val costume_reference_no: Int,
    val created_at: String,
    val id: Int,
    val order_id: Int,
    val qty: Int,
    val status: Int,
    val updated_at: String,
    val user_id: Int,
    val user_warning_status: Any
)