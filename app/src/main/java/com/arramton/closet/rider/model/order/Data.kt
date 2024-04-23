package com.arramton.closet.rider.model.order

data class Data(
    val address: Address,
    val address_id: Int,
    val coupon_id: Any,
    val created_at: String,
    val delivery_charge: Int,
    val delivery_date: Any,
    val delivery_earn: Any,
    val delivery_user: Int,
    val delivery_user_status: Int,
    val discount: String,
    val gst: String,
    val id: Int,
    val order_reference_no: Int,
    val order_status_id: Int,
    val payment_mode: Any,
    val payment_status: Int,
    val pickup_date: String,
    val pickup_earn: Int,
    val pickup_time: String,
    val sub_total: String,
    val total: String,
    val updated_at: String,
    val user_id: Int
)