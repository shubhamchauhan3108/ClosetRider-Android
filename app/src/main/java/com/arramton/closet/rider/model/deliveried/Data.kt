package com.arramton.closet.rider.model.deliveried

data class Data(
    val address_id: Int,
    val costume: Any,
    val coupon: Any,
    val created_at: String,
    val delivery_boy_earning: Int,
    val delivery_order_status_id: Int,
    val delivery_otp: Any,
    val delivery_otp_time: Any,
    val delivery_user_id: Int,
    val drop_off_date: String,
    val drop_time: String,
    val id: Int,
    val order: OrderItem,
    val order_reference_no: String,
    val order_status: OrderStatus,
    val updated_at: String,
    val user_id: Int
)